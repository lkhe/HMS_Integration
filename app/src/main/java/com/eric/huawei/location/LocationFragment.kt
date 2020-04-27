package com.eric.huawei.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat

import com.eric.huawei.R
import com.eric.huawei.map.MapFragment
import com.huawei.hmf.tasks.OnFailureListener
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.*
import com.huawei.hms.maps.model.LatLng
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment : Fragment() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: EricLocationCallback
    private lateinit var latlngTextView: TextView
    private lateinit var timeStampTextView: TextView

     inner class EricLocationCallback : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.apply {
                onLocationChange(this.lastLocation)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_location, container, false)

        if (!hasPermissions(
                context!!,
                RUNTIME_PERMISSIONS
            )
        ) {
            //no permission, request
            ActivityCompat.requestPermissions(requireActivity(),
                RUNTIME_PERMISSIONS, 0)
        }

        latlngTextView = rootView.findViewById(R.id.latlng)
        timeStampTextView = rootView.findViewById(R.id.timeStamp)

        startLocationUpdate()

        // Inflate the layout for this fragment
        return rootView
    }

    private fun startLocationUpdate() {

        //create location request to start receiving update
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10000)
            .setFastestInterval(2000)

        //create location settings request with location request
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        //check if location settings are allowed
        LocationServices.getSettingsClient(requireActivity())
            .checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                LocationServices.getFusedLocationProviderClient(requireActivity()).requestLocationUpdates(locationRequest, EricLocationCallback(), Looper.getMainLooper())
            }
            .addOnFailureListener {
                Timber.e(it.localizedMessage)
            }
    }


     fun onLocationChange(location: Location) {
        latlng.latitude = location.latitude
        latlng.longitude = location.longitude
        latlngTextView.text = latlng.latitude.toString() + "," + latlng.longitude.toString()
        timeStampTextView.text = SimpleDateFormat("dd/MM HH:mm:ss").format(Date())
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            .addOnSuccessListener { Timber.i("location updates removed") }
            .addOnFailureListener { Timber.i(it.localizedMessage) }
    }

    companion object {

        var latlng: LatLng = LatLng(51.0,10.0)

        private val RUNTIME_PERMISSIONS:Array<String> = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

        @JvmStatic
        fun newInstance() = LocationFragment()

        private fun hasPermissions(context: Context, permissions:Array<String>) : Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions!=null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        //not granted
                        return false
                    }
                }
            }
            return true
        }
    }
}

package com.eric.huawei.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.eric.huawei.R
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private var map: HuaweiMap? = null
    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        if (!hasPermissions(
                context!!,
                RUNTIME_PERMISSIONS
            )
        ) {
            //no permission, request
            ActivityCompat.requestPermissions(requireActivity(),
                RUNTIME_PERMISSIONS, 0)
        }

        mapView = rootView.findViewById(R.id.mapView)
        mapView.onCreate(null)
        mapView.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(map: HuaweiMap?) {
        this.map = map
        Timber.i("onMapReady")
    }

    //TODO: permission denied

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()

        private fun hasPermissions(context:Context, permissions:Array<String>) : Boolean {
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

        private val RUNTIME_PERMISSIONS:Array<String> = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
}

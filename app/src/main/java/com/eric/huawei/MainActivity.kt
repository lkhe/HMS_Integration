package com.eric.huawei

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.eric.huawei.base.BaseMainActivity
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : BaseMainActivity() {

    private var pushToken:String? = null
    private lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
        job = CoroutineScope(Dispatchers.Default + SupervisorJob()).launch { getToken(this@MainActivity) }
    }

    private suspend fun getToken(activity: Activity?) : String? {

        var pushToken : String?  = null

        activity?.also {
            val appId = AGConnectServicesConfig.fromContext(it).getString("client/app_id")

            withContext(Dispatchers.IO) {
                pushToken  = HmsInstanceId.getInstance(it).getToken(appId, "HMS")
            }
        }
        Timber.i("Push Token: " + pushToken)
        return pushToken
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }
}

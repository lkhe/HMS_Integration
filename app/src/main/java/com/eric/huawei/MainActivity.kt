package com.eric.huawei

import android.os.Bundle
import com.eric.huawei.base.BaseMainActivity
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : BaseMainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
        CoroutineScope(Dispatchers.Default + SupervisorJob()).launch { getToken(this@MainActivity) }
    }

    private suspend fun getToken(mainActivity: MainActivity) {
        val appId : String = AGConnectServicesConfig.fromContext(mainActivity).getString("client/app_id")
        var pushToken : String? = null

        withContext(Dispatchers.IO) {
           pushToken  = HmsInstanceId.getInstance(mainActivity).getToken(appId, "HMS")
        }
        pushToken?.let {
            Timber.i(pushToken)
        }
    }
}

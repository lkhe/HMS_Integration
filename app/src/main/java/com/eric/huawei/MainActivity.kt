package com.eric.huawei

import android.os.Bundle
import com.eric.huawei.Base.BaseMainActivity
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : BaseMainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch { getToken() }
    }

    private suspend fun getToken() {
        val appId : String = AGConnectServicesConfig.fromContext(applicationContext).getString("client/app_id")
        var pushToken : String? = null

        withContext(Dispatchers.IO) {
           pushToken  = HmsInstanceId.getInstance(applicationContext).getToken(appId, "Eric")
        }
        pushToken?.let {
            Timber.i(pushToken)
        }
    }
}

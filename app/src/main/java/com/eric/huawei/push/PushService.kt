package com.eric.huawei.push

import android.annotation.SuppressLint
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import timber.log.Timber
import java.util.*

class PushService : HmsMessageService() {

    /*
        For notification message, no matter if app process is foreground/background/killed,
        notification appeared in the tray

        If device not awake, notification message appears on the lock screen also

        Verified that upon new app install, a new token is received

     */


    /*
        Used only for devices with EMUI version earlier than 10.0 / when new token is re-generated
     */

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Timber.i(token)
    }

    /*
        used to receive and process data message
     */

    @SuppressLint("BinaryOperationInTimber")
    override fun onMessageReceived(remoteMessage : RemoteMessage?) {
        val map = remoteMessage?.let {
            it.dataOfMap
        }
        Timber.i(map.toString())
    }
}
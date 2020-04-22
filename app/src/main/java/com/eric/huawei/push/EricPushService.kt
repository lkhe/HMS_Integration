package com.eric.huawei.push

import android.annotation.SuppressLint
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import timber.log.Timber
import java.util.*

class EricPushService : HmsMessageService() {

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
    override fun onMessageReceived(message: RemoteMessage?) {
        if (message == null) {
            Timber.i("message is null")
            return
        } else {
            Timber.i("getCollapseKey: " + message.getCollapseKey()
                    + "\n getData: " + message.getData()
                    + "\n getFrom: " + message.getFrom()
                    + "\n getTo: " + message.getTo()
                    + "\n getMessageId: " + message.getMessageId()
                    + "\n getOriginalUrgency: " + message.getOriginalUrgency()
                    + "\n getUrgency: " + message.getUrgency()
                    + "\n getSendTime: " + message.getSentTime()
                    + "\n getMessageType: " + message.getMessageType()
                    + "\n getTtl: " + message.getTtl());
        }


        val notification = message.notification
        if (notification != null) {
            Timber.i("\n getImageUrl: " + notification.getImageUrl()
                    + "\n getTitle: " + notification.getTitle()
                    + "\n getTitleLocalizationKey: " + notification.getTitleLocalizationKey()
                    + "\n getTitleLocalizationArgs: " + Arrays.toString(notification.getTitleLocalizationArgs())
                    + "\n getBody: " + notification.getBody()
                    + "\n getBodyLocalizationKey: " + notification.getBodyLocalizationKey()
                    + "\n getBodyLocalizationArgs: " + Arrays.toString(notification.getBodyLocalizationArgs())
                    + "\n getIcon: " + notification.getIcon()
                    + "\n getSound: " + notification.getSound()
                    + "\n getTag: " + notification.getTag()
                    + "\n getColor: " + notification.getColor()
                    + "\n getClickAction: " + notification.getClickAction()
                    + "\n getChannelId: " + notification.getChannelId()
                    + "\n getLink: " + notification.getLink()
                    + "\n getNotifyId: " + notification.getNotifyId());
        }

        //TODO:process data message

    }
}
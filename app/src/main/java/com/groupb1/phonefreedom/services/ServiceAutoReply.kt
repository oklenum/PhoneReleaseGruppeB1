package com.groupb1.phonefreedom.services

import android.Manifest
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.provider.Telephony
import android.telephony.SmsManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.groupb1.phonefreedom.data.Reply
import com.vmadalin.easypermissions.EasyPermissions

class ServiceAutoReply : Service() {

    var phoneNumber = ""
    var smsText = ""
    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private var yourReceiver: BroadcastReceiver? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }



    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        //autoReply()

        val theFilter = IntentFilter()
        theFilter.addAction(ACTION)
        this.yourReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        phoneNumber = sms.displayOriginatingAddress.toString()
                        sendSMS(phoneNumber, Reply.description)
                    }
                }
            }
        }
        registerReceiver(yourReceiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))

    }


    fun sendSMS(phoneNumber: String, msg: String) {
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, "me", msg, null, null)
    }
    private fun autoReply(){
        var br = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        phoneNumber = sms.displayOriginatingAddress.toString()
                        sendSMS(phoneNumber, Reply.description)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    fun editAutoReply() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                ""
            }

        val notification: Notification = Notification.Builder(this, channelId)
            .setContentTitle("title")
            .setContentText("text")
            .build()
        startForeground(2001, notification)

        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_DEFAULT)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(yourReceiver)
    }
}
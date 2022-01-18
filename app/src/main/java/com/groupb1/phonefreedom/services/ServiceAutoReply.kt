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
import android.provider.Telephony
import android.telephony.SmsManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class ServiceAutoReply : Service() {

    var phoneNumber = ""
    var smsText = ""
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }



    override fun onCreate() {
        super.onCreate()
        //setContentView(R.layout.activity_main)

        mContext = applicationContext


        checkPermissionSMS()
        autoReply()


    }

    fun checkPermissionSMS() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_NUMBERS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                mActivity as Activity, arrayOf(
                    Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE),
                111)
        }
    }
    fun checkPermissionDND() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                mActivity as Activity, arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.RECEIVE_SMS),
                111)
        }
    }

    fun sendSMS(phoneNumber: String) {
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, "me", "pil af med dig", null, null)
    }
    private fun autoReply(){
        var br = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        phoneNumber = sms.displayOriginatingAddress.toString()
                        sendSMS(phoneNumber)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    fun editAutoReply() {

    }
/*
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            autoReply()
    }
 */

    /*
        private fun permissionCheck() {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        }

     */
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
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

}
package com.groupb1.phonefreedom.services

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.provider.Settings
import androidx.annotation.RequiresApi

class ServiceDisturb : Service() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private var mReceiver: BroadcastReceiver? = null
    private lateinit var mNotificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()

        // Get the application context
        mContext = applicationContext

        val filter = IntentFilter()
        filter.addAction(ACTION)
        this.mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                changeInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
            }
        }
        registerReceiver(mReceiver, IntentFilter("Service Active"))

        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


    }
    private fun changeInterruptionFilter(interruptionFilter: Int) {
        // If api level minimum 23
        // If notification policy access granted for this package
        if (mNotificationManager.isNotificationPolicyAccessGranted) {

            // Set the interruption filter
            mNotificationManager.setInterruptionFilter(interruptionFilter)
        } else {
            // If notification policy access not granted for this package
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}
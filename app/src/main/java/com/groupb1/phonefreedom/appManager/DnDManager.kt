package com.groupb1.phonefreedom.appManager

import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.widget.LinearLayout
import android.widget.TextView
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import com.groupb1.phonefreedom.R
import android.os.Build
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi

open class DnDManager : AppCompatActivity() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private lateinit var mNotificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // Get the application context
        mContext = applicationContext
        mActivity = this@DnDManager
        /*
            NotificationManager
                Class to notify the user of events that happen. This is how you tell
                the user that something has happened in the background.
        */
        // Get the notification manager instance
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Total silence the device, turn off all notifications

        changeInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        finish()
    }

    private fun changeInterruptionFilter(interruptionFilter: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // If api level minimum 23
            // If notification policy access granted for this package
            if (mNotificationManager!!.isNotificationPolicyAccessGranted) {

                // Set the interruption filter
                mNotificationManager!!.setInterruptionFilter(interruptionFilter)
            } else {
                // If notification policy access not granted for this package
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(intent)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun startDoNotDisturb () {

        mActivity = this@DnDManager


        changeInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
    }
}
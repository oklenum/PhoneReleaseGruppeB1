package com.groupb1.phonefreedom.appManager

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.os.Build
import android.content.Intent
import android.provider.Settings
import android.content.DialogInterface




open class DnDOnActivity : AppCompatActivity() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private lateinit var mNotificationManager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // Get the application context
        mContext = applicationContext
        mActivity = this@DnDOnActivity
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
}
package com.groupb1.phonefreedom.appManager

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity


open class DnDOnActivity : AppCompatActivity() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private lateinit var mNotificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Get the application context
        mContext = applicationContext
        mActivity = this@DnDOnActivity

        // Get the notification manager instance
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Turns on DnD

        changeInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        finish()
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
}
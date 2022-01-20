package com.groupb1.phonefreedom.appManager

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.groupb1.phonefreedom.services.ServiceAutoReply
import com.groupb1.phonefreedom.services.ServiceDisturb

open class StopActivities : AppCompatActivity() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private lateinit var mNotificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = applicationContext
        mActivity = this@StopActivities

        this.stopService(Intent(mContext, ServiceDisturb()::class.java))
        this.stopService(Intent(mContext, ServiceAutoReply()::class.java))
    }
}
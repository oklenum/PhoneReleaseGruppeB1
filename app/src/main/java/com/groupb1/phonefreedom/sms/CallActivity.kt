package com.groupb1.phonefreedom.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.Telephony
import com.groupb1.phonefreedom.R

class CallActivity : AppCompatActivity() {
    val smsManager = SmsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        smsManager.checkPermissionSMS()
        autoReplyCall()

    }

    private fun autoReplyCall() {
        var br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    smsManager.phoneNumber = CallLog.Calls.NUMBER
                    smsManager.sendSMS(smsManager.phoneNumber)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}

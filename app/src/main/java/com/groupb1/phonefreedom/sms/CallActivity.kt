package com.groupb1.phonefreedom.sms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.groupb1.phonefreedom.R

class CallActivity : AppCompatActivity() {
    val smsManager = SmsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        smsManager.checkPermissionSMS()
    }
}
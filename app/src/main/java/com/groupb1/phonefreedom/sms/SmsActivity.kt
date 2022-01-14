package com.groupb1.phonefreedom.sms

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import com.groupb1.phonefreedom.R

class SmsActivity: AppCompatActivity() { // Delete class.

    val smsManager = SmsManager()
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.fragment_second)

        mContext = applicationContext
        mActivity = this@SmsActivity

        //smsManager.checkPermissionSMS()
        autoReply()
        finish()
    }

    private fun autoReply(){
        var br = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        smsManager.phoneNumber = sms.displayOriginatingAddress.toString()
                        smsManager.sendSMS(smsManager.phoneNumber)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}
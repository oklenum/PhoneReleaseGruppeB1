package com.groupb1.phonefreedom.appManager

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.provider.Telephony
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.groupb1.phonefreedom.sms.SmsActivity

open class AutoReplyManager: AppCompatActivity() {

    var phoneNumber = ""
    var smsText = ""
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mContext = applicationContext
        mActivity = this@AutoReplyManager

        checkPermissionSMS()
        //autoReply()
        finish()

    }

    fun checkPermissionSMS() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_NUMBERS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG),
                111)
        }
    }
    fun checkPermissionCall() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE),
                111)
        }
    }
    fun checkPermissionDND() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.RECEIVE_SMS),
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
    private fun autoReplyOnCall(){
        var br = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                var number = getRecentCallerNumber()
                if (number != null) {
                    sendSMS(number)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.intent.action.PHONE_STATE"))
    }
    private fun getRecentCallerNumber(): String? {
        val managedCursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null, null, null, null
        )
        val number = managedCursor?.getColumnIndex(CallLog.Calls.NUMBER)
        managedCursor?.moveToLast()
        val phoneNumber = number?.let { managedCursor?.getString(it) }

        return phoneNumber
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
}
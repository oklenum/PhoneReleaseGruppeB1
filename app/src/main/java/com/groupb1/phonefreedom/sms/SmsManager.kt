package com.groupb1.phonefreedom.sms

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.provider.Telephony
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity

class SmsManager {

    var phoneNumber = ""
    var smsText = ""

    fun checkPermissionSMS() {
        if(ActivityCompat.checkSelfPermission(SmsActivity(),android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(),android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(),android.Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(),android.Manifest.permission.READ_PHONE_NUMBERS)!= PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(),android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(SmsActivity(), arrayOf(
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE),
                111)
        }
    }
    fun checkPermissionDND() {
        if(ActivityCompat.checkSelfPermission(SmsActivity(),android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SmsActivity(), arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.RECEIVE_SMS),
                111)
        }
    }

    fun sendSMS(phoneNumber: String) {
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, "me", "pil af med dig", null, null)
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
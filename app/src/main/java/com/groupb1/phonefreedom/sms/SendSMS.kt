package com.groupb1.phonefreedom.sms

import android.app.PendingIntent
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class SendSMS {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun sendSMS(phoneNumber: String, message: String) {

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, )
        }
    }
}
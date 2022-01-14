package com.groupb1.phonefreedom.autoReplyCall

import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import com.groupb1.phonefreedom.sms.SmsActivity

class CallManager {

    var phoneNumber = ""
    var smsText = ""

    fun checkPermissionCall() {
        if (ActivityCompat.checkSelfPermission(SmsActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SmsActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            
                ActivityCompat.requestPermissions(SmsActivity(), arrayOf(
                    Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_PHONE_STATE), 111)
        }
    }

    fun sendCallSms(phoneNumber: String) {
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, "me", "pil af med dig", null, null)
    }
}
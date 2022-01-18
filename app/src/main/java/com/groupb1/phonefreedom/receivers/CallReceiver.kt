package com.groupb1.phonefreedom.receivers

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.CallLog
import android.telecom.Call
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import android.telephony.PhoneStateListener
import android.telecom.Call.Details
import android.telecom.InCallService
import android.telecom.TelecomManager
import android.telephony.PhoneNumberUtils
import androidx.annotation.RequiresApi
import com.groupb1.phonefreedom.MainActivity
import com.groupb1.phonefreedom.appManager.AutoReplyManager
import com.groupb1.phonefreedom.databinding.ActivityMainBinding


class CallReceiver : BroadcastReceiver() {

    var manager = AutoReplyManager()

    override fun onReceive(context: Context?, intent: Intent?) {

        val number = getRecentCallerNumber()

        if (number != null) {
            manager.sendSMS(number)
        }


    }

    private fun getRecentCallerNumber(): String? {
        val managedCursor = manager.contentResolver.query(CallLog.Calls.CONTENT_URI,
            null, null, null, null
        )
        val number = managedCursor?.getColumnIndex(CallLog.Calls.NUMBER)
        managedCursor?.moveToLast()
        val phoneNumber = number?.let { managedCursor?.getString(it) }

        return phoneNumber
    }

}
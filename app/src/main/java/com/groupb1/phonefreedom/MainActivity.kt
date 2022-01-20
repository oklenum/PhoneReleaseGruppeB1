package com.groupb1.phonefreedom

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.Settings
import android.provider.Telephony
import android.telecom.Call
import android.telecom.InCallService
import android.telephony.*
import android.util.Log
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.lang.Long
import java.util.*
import kotlin.math.log

open class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    val phoneStateListener = PhoneStateListener() {

    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


/*
    open fun getRecentCallerNumber(): String? {
        val managedCursor = contentResolver.query(CallLog.Calls.CONTENT_URI,
            null, null, null, null
        )
        val number = managedCursor?.getColumnIndex(CallLog.Calls.NUMBER)
        managedCursor?.moveToLast()
        val phoneNumber = number?.let { managedCursor?.getString(it) }

        return phoneNumber
    }

 */
/*
    open fun getCallDetails(): String {
        val sb = StringBuffer()
        val managedCursor = managedQuery(
            CallLog.Calls.CONTENT_URI, null,
            null, null, null
        )
        val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
        sb.append("Call Details :")
        while (managedCursor.moveToFirst()) {
            val phNumber = managedCursor.getString(number)
            val callType = managedCursor.getString(type)
            val callDate = managedCursor.getString(date)
            val callDayTime = Date(Long.valueOf(callDate))
            val callDuration = managedCursor.getString(duration)
            var dir: String? = null
            val dircode = callType.toInt()
            when (dircode) {
                CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"
                CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"
                CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
            }
            sb.append(
                """
Phone Number:--- $phNumber
Call Type:--- $dir
Call Date:--- $callDayTime
Call duration in sec :--- $callDuration"""
            )
            sb.append("\n----------------------------------")
        }
        managedCursor.close()
        return sb.toString()
    }

 */

}


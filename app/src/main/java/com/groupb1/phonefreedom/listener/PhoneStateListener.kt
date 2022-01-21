package com.groupb1.phonefreedom.listener

import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log

class PhoneStateListener : PhoneStateListener() {

    var phoneRinging = false
    var phoneNumber = "123"
    var lastState = TelephonyManager.CALL_STATE_IDLE

    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE ->
                if(lastState != state){
                    Log.d("TAG", "IDLE! \n" + "$phoneNumber")
                    phoneRinging = false
                }
            TelephonyManager.CALL_STATE_OFFHOOK ->
            if (lastState != state){
                Log.d("TAG", "OFFHOOK! \n" + "$phoneNumber")
                phoneRinging = false
            }
            TelephonyManager.CALL_STATE_RINGING ->
            if(lastState != state){
                Log.d("TAG", "RINGING! \n" + "$phoneNumber")
                phoneRinging = true
            }
        }
        lastState = state
    }
    @JvmName("getLastState1")
    fun getLastState(): Int {
        return lastState
    }
}
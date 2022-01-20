package com.groupb1.phonefreedom.appManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.groupb1.phonefreedom.data.CheckSer
import com.groupb1.phonefreedom.services.ServiceAutoReply
import com.groupb1.phonefreedom.services.ServiceDisturb

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val stopDnD = Intent(context, DnDOffActivity::class.java)
        val stopDnDService = Intent(context, ServiceDisturb::class.java)
        val stopARService = Intent(context, ServiceAutoReply::class.java)
        stopDnD.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (intent?.action == "StopServices") {
            Log.d("Alarm","AlarmReceived")
            //context?.startActivity(stopDnD)
            context?.stopService(stopDnDService)
            context?.stopService(stopARService)
            CheckSer.check = "Stopped"
        }
    }
}

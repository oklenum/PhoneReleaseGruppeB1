package com.groupb1.phonefreedom

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.groupb1.phonefreedom.appManager.DnDOnActivity
import com.groupb1.phonefreedom.appManager.AutoReplyManager
import com.groupb1.phonefreedom.appManager.StopActivities

import com.groupb1.phonefreedom.services.ServiceAutoReply
import com.groupb1.phonefreedom.services.ServiceDisturb
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.app.AlarmManager

import androidx.core.content.ContextCompat.getSystemService

import android.app.PendingIntent
import android.content.Context
import androidx.core.content.ContextCompat
import com.groupb1.phonefreedom.appManager.Receiver
import com.groupb1.phonefreedom.data.CheckSer
import java.util.*


class SecondFragment : Fragment() {

    private lateinit var timeLeft: TextView
    private lateinit var date: TextView
    private lateinit var hourId: String
    private lateinit var minuteId: String
    private lateinit var dayId: String
    private lateinit var monthId: String
    private lateinit var yearId: String

    companion object {
        const val HOUR = "hour"
        const val MINUTE = "minute"
        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "year"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hourId = it.getString(HOUR).toString()
            minuteId = it.getString(MINUTE).toString()
            dayId = it.getString(DAY).toString()
            monthId = it.getString(MONTH).toString()
            yearId = it.getString(YEAR).toString()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formatted = currentTime.format(formatter).toString()
        val currentDate = LocalDate.now()
        val formatDate = DateTimeFormatter.ofPattern("dd-MM-YYYY")
        val formattedDate = currentDate.format(formatDate).toString()

        val intent = Intent(activity, DnDOnActivity()::class.java) // Activates DND
        startActivity(intent)

        timeLeft = view.findViewById(R.id.timeLeft)
        date = view.findViewById(R.id.date)
        CheckSer.check = ""
        val intent2 = Intent(activity, AutoReplyManager::class.java) // Activates SMS Auto reply
        startActivity(intent2)

        timeLeft.text = "${hourId}:${minuteId}"

        if (dayId == "") {
            date.text = formattedDate
        } else {
            date.text = "${dayId}-${monthId}-${yearId}"
        }

        val deactivateButton = view.findViewById<ImageButton>(R.id.deactivateButton)
        deactivateButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_firstFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().startService(Intent(activity, ServiceDisturb()::class.java))
        requireActivity().startService(Intent(activity, ServiceAutoReply()::class.java))

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Receiver::class.java)

        intent.action = "StopServices"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val calendar = Calendar.getInstance()
        val calSet = calendar.clone() as Calendar
        calSet.set(Calendar.HOUR_OF_DAY, hourId.toInt())
        calSet.set(Calendar.MINUTE, minuteId.toInt())
        calSet.set(Calendar.SECOND, 0)
        calSet.set(Calendar.MILLISECOND, 0)

        val msUntilTriggerHour: Long = 10000

        val alarmTimeAtUTC: Long = calendar.timeInMillis - System.currentTimeMillis()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(calSet.timeInMillis, pendingIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calSet.timeInMillis,
                pendingIntent
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        if (CheckSer.check == "Stopped") {
            view?.let { Navigation.findNavController(it).navigate(
                R.id.action_secondFragment_to_firstFragment) }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}




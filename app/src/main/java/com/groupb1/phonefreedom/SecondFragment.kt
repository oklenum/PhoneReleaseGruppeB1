package com.groupb1.phonefreedom

import android.Manifest
import android.content.Intent
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.contentValuesOf
import androidx.navigation.Navigation
import com.groupb1.phonefreedom.appManager.DnDOnActivity
import com.groupb1.phonefreedom.appManager.AutoReplyManager
import com.groupb1.phonefreedom.sms.SmsActivity
import com.groupb1.phonefreedom.appManager.DnDOffActivity
import com.groupb1.phonefreedom.presetDetail.PresetDetailActivity
import android.os.Handler;
import android.widget.ProgressBar;

import com.groupb1.phonefreedom.services.ServiceAutoReply
import com.groupb1.phonefreedom.services.ServiceDisturb
import com.vmadalin.easypermissions.EasyPermissions
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formatted = currentTime.format(formatter).toString()
        val currentDate = LocalDate.now().toString()

        val intent = Intent(activity, DnDOnActivity()::class.java) // Activates DND
        startActivity(intent)


        timeLeft = view.findViewById(R.id.timeLeft)
        date = view.findViewById(R.id.date)

        val intent2 = Intent(activity, AutoReplyManager::class.java) // Activates SMS Auto reply
        startActivity(intent2)




        //requireActivity().startService(Intent(activity, ServiceDisturb()::class.java))
        //requireActivity().startService(Intent(activity, ServiceAutoReply()::class.java))

        timeLeft.text = "${hourId}:${minuteId}"
        date.text = "${dayId}-${monthId}-${yearId}"

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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }



}




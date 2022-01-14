package com.groupb1.phonefreedom

import android.content.Intent
import android.app.NotificationManager
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
import androidx.navigation.Navigation
import com.groupb1.phonefreedom.appManager.DnDOnActivity
import com.groupb1.phonefreedom.appManager.AutoReplyManager
import com.groupb1.phonefreedom.sms.SmsActivity
import com.groupb1.phonefreedom.appManager.DnDOffActivity
import com.groupb1.phonefreedom.presetDetail.PresetDetailActivity

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {

    // TODO: Rename and change types of parameters
    val firstFragment = FirstFragment()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private lateinit var mNotificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val intent = Intent(activity, DnDOnActivity()::class.java) // Activates DND
        startActivity(intent)


        val intent2 = Intent(activity, AutoReplyManager::class.java) // Activates SMS Auto reply
        startActivity(intent2)

        //dndManager.startDoNotDisturb()

        val deactivateButton = view.findViewById<ImageButton>(R.id.deactivateButton)
        deactivateButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_firstFragment)

        }

        return view

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    // ikke færdiggjort
    //private fun getTimePicked() {
        //findViewById<TextView>(R.id.timeLeft)
       // firstFragment.showTimePickerDialog()
}


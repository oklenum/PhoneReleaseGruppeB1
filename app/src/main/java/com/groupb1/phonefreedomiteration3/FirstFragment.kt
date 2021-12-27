package com.groupb1.phonefreedomiteration3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    lateinit var timePicker: TimePickerHelper
    lateinit var datePicker: DatePickerHelper
    private lateinit var timeTextView: TextView
    private lateinit var dateTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        datePicker = DatePickerHelper(this.requireContext())
        timePicker = TimePickerHelper(this.requireContext(), true, false)
        timeTextView = view.findViewById(R.id.timeView)
        dateTextView = view.findViewById(R.id.dateView)
        val timeButton = view.findViewById<ImageButton>(R.id.selectTimeBtn)
        val dateButton = view.findViewById<ImageButton>(R.id.selectDateBtn)
        timeButton.setOnClickListener {
            showTimePickerDialog()
        }
        dateButton.setOnClickListener {
            showDatePickerDialog()
        }

        val activateButton = view.findViewById<Button>(R.id.activateButton)
        activateButton.setOnClickListener {Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment) }

        return view
    }

    private fun showTimePickerDialog() {
        val cal = Calendar.getInstance()
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)
        timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
            override fun onTimeSelected(hourOfDay: Int, minute: Int) {
                val hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "${hourOfDay}"
                val minuteStr = if (minute < 10) "0${minute}" else "${minute}"
                timeTextView.text = "${hourOfDay}:${minuteStr}"
            }
        })
    }
    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                dateTextView.text = "${dayStr}-${monthStr}-${year}"
            }
        })
    }


}
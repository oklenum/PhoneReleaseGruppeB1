package com.groupb1.phonefreedom

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.groupb1.phonefreedom.addPreset.AddPresetActivity
import com.groupb1.phonefreedom.addPreset.PRESET_DESCRIPTION
import com.groupb1.phonefreedom.addPreset.PRESET_NAME
import com.groupb1.phonefreedom.data.Preset
import com.groupb1.phonefreedom.presetDetail.PresetDetailActivity
//import com.groupb1.phonefreedom.presetList.PRESET_ID
//import com.groupb1.phonefreedom.presetList.PresetsListActivity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val PRESET_ID = "preset id"

class FirstFragment : Fragment() {

    lateinit var timePicker: TimePickerHelper
    lateinit var datePicker: DatePickerHelper
    private lateinit var timeTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var dayOfTheWeekView: TextView
    //private lateinit var recyclerView: RecyclerView
    private val presetsListViewModel by viewModels<PresetsListViewModel> {
        PresetsListViewModelFactory(this)
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val presetName = it.data?.getStringExtra(PRESET_NAME)
            val presetDescription = it.data?.getStringExtra(PRESET_DESCRIPTION)
            presetsListViewModel.insertPreset(presetName, presetDescription)
        }
    }
    //var notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val startTime = LocalTime.of(17, 0).toString()
        val currentDate = LocalDate.now().toString()
        datePicker = DatePickerHelper(this.requireContext())
        timePicker = TimePickerHelper(this.requireContext(), true, false)
        timeTextView = view.findViewById(R.id.timeView)
        dateTextView = view.findViewById(R.id.dateView)
        val timeButton = view.findViewById<ImageButton>(R.id.selectTimeBtn)
        val dateButton = view.findViewById<ImageButton>(R.id.selectDateBtn)
        val actionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        timeTextView.text = startTime
        dateTextView.text = currentDate
        timeButton.setOnClickListener {
            showTimePickerDialog()
        }
        dateButton.setOnClickListener {
            showDatePickerDialog()
        }

        val activateButton = view.findViewById<ImageButton>(R.id.activateButton)
        activateButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment)
        }


        //recyclerView.view.findViewById(R.id.presetList)


        actionButton.setOnClickListener {
            actionButtonOnClick()
        }

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
                //val w = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH)
                //val dayOfWeek = DateFormatSymbols().shortWeekdays[2]
                //dayOfTheWeekView.text = dayOfWeek
                dateTextView.text = "${dayStr}-${monthStr}-${year}"

            }
        })
    }

    private fun turnOnDND() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.presetList)
        val presetsAdapter = PresetsAdapter { preset -> adapterOnClick(preset)  }
        recyclerView.adapter = presetsAdapter
        //recyclerView = view.findViewById(R.id.presetList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.adapter = presets
        //val intent = Intent(activity, PresetsListActivity::class.java)
        //startActivity(intent)
        presetsListViewModel.presetsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                presetsAdapter.submitList(it as MutableList<Preset>)

            }
        })

    }

    private fun adapterOnClick(preset: Preset) {
        val intent = Intent(activity, PresetDetailActivity()::class.java)
        intent.putExtra(PRESET_ID, preset.id)
        startActivity(intent)
    }

    private fun actionButtonOnClick() {
        val intent = Intent(activity, AddPresetActivity::class.java)
        getResult.launch(intent)
    }

}
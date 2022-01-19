package com.groupb1.phonefreedom

import android.app.Activity
import android.app.AlertDialog
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
import com.groupb1.phonefreedom.appManager.DnDOffActivity
import com.groupb1.phonefreedom.data.Preset
import com.groupb1.phonefreedom.presetDetail.PresetDetailActivity
import com.vmadalin.easypermissions.EasyPermissions
//import com.groupb1.phonefreedom.presetList.PRESET_ID
//import com.groupb1.phonefreedom.presetList.PresetsListActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

import android.widget.CheckBox
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences
import com.groupb1.phonefreedom.presetList.PresetsAdapter
import com.groupb1.phonefreedom.services.ServiceAutoReply
import com.groupb1.phonefreedom.services.ServiceDisturb

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
    private lateinit var textView6: TextView
    private var hourStr: String = ""
    private var minuteStr: String = ""
    private var dayStr: String = ""
    private var monthStr: String = ""
    private var yearStr: String = ""
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val startTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formatted = startTime.format(formatter).toString()
        val currentDate = LocalDate.now().toString()
        datePicker = DatePickerHelper(this.requireContext())
        timePicker = TimePickerHelper(this.requireContext(), true, false)
        timeTextView = view.findViewById(R.id.timeView)
        dateTextView = view.findViewById(R.id.dateView)
        val timeButton = view.findViewById<ImageButton>(R.id.selectTimeBtn)
        val dateButton = view.findViewById<ImageButton>(R.id.selectDateBtn)
        val actionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)
        val checkButton = view.findViewById<Button>(R.id.button3)
        timeTextView.text = formatted
        timeTextView.text = formatted
        dateTextView.text = currentDate

        timeButton.setOnClickListener {
            showTimePickerDialog()

        }


        dateButton.setOnClickListener {
            showDatePickerDialog()
        }

        val activateButton = view.findViewById<ImageButton>(R.id.activateButton)
        activateButton.setOnClickListener {
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    hour = hourStr, minute = minuteStr, day = dayStr,
                    month = monthStr, year = yearStr)
            Navigation.findNavController(view).navigate(action)
        }

        settingsButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_settingsFragment)
        }

        if (!EasyPermissions.hasPermissions(requireContext(),
                android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
                    checkButton.visibility = View.VISIBLE
            checkButton.setOnClickListener {
                Navigation.findNavController(view).navigate(
                R.id.action_firstFragment_to_permissionFragment)
            }
        }

        if (EasyPermissions.hasPermissions(requireContext(),
            android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        } else {

        }

        val alertBuilder = AlertDialog.Builder(requireActivity())
        val mView: View = layoutInflater.inflate(R.layout.dialog_permissions, null)
        val mCheckBox = mView.findViewById<CheckBox>(R.id.checkBox)
        alertBuilder.setTitle("We Need Permission")
        alertBuilder.setMessage("First Time using Phone Freedom, we need access to DoNotDisturb, Press Ok to redirect to access settings")
        alertBuilder.setView(mView)
        alertBuilder.setPositiveButton("OK"
        ) { dialogInterface, i ->
            val intent2 = Intent(activity, DnDOffActivity()::class.java)
            startActivity(intent2)
        }

        val intent3 = Intent(activity, DnDOffActivity()::class.java)
        startActivity(intent3)

        val mDialog: AlertDialog = alertBuilder.create()
        mDialog.show()
        mCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                storeDialogStatus(true)
            } else {
                storeDialogStatus(false)
            }
        }

        if (getDialogStatus()) {
            mDialog.hide()
        } else {
            mDialog.show()
        }


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
                hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "${hourOfDay}"
                minuteStr = if (minute < 10) "0${minute}" else "${minute}"
                timeTextView.text = "${hourStr}:${minuteStr}"
                textView6.text = hourStr

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
                dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                monthStr = if (mon < 10) "0${mon}" else "${mon}"
                yearStr = "$year"
                dateTextView.text = "${dayStr}-${monthStr}-${yearStr}"

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.presetList)
        val presetsAdapter = PresetsAdapter { preset -> adapterOnClick(preset)  }
        recyclerView.adapter = presetsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        presetsListViewModel.presetsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                presetsAdapter.submitList(it as MutableList<Preset>)

            }
        })

        requireActivity().stopService(Intent(activity, ServiceDisturb()::class.java))
        requireActivity().stopService(Intent(activity, ServiceAutoReply()::class.java))

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

    private fun storeDialogStatus(isChecked: Boolean) {
        val mSharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("CheckItem", MODE_PRIVATE)
        val mEditor = mSharedPreferences.edit()
        mEditor.putBoolean("item", isChecked)
        mEditor.apply()
    }

    private fun getDialogStatus(): Boolean {
        val mSharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("CheckItem", MODE_PRIVATE)
        return mSharedPreferences.getBoolean("item", false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }



}
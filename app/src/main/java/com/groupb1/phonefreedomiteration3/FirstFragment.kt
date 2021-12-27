package com.groupb1.phonefreedomiteration3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.navigation.Navigation
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val activateButton = view.findViewById<Button>(R.id.activateButton)
        activateButton.setOnClickListener {Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment)

        pickDate()
        }

        private fun getTime() {
            val cal: Calendar.getInstance()
            hour = cal.get(Calendar.HOUR)
        }

        return view
    }

    override fun onDateSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

    }


}
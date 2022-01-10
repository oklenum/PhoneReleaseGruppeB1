package com.groupb1.phonefreedom.addPreset

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.groupb1.phonefreedom.R


const val PRESET_NAME = "name"
const val PRESET_DESCRIPTION = "description"

class AddPresetActivity : AppCompatActivity() {
   private lateinit var setPresetName: TextInputEditText
   private lateinit var setPresetDescription: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_preset_layout)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addPreset()
        }
        setPresetName = findViewById(R.id.add_preset_name)
        setPresetDescription = findViewById(R.id.add_preset_description)
    }

    private fun addPreset() {
        val resultIntent = Intent()

        if (setPresetName.text.isNullOrEmpty() || setPresetDescription.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = setPresetName.text.toString()
            val description = setPresetDescription.text.toString()
            resultIntent.putExtra(PRESET_NAME, name)
            resultIntent.putExtra(PRESET_DESCRIPTION, description)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

}
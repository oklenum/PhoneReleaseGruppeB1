package com.groupb1.phonefreedom.presetDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.groupb1.phonefreedom.PRESET_ID
import com.groupb1.phonefreedom.R

/**
 * A simple [Fragment] subclass.
 * Use the [PresetDetailActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class PresetDetailActivity : AppCompatActivity() {

    private val presetDetailViewModel by viewModels<PresetDetailViewModel> {
        PresetDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preset_detail_activity)

        var currentPresetId: Long? = null


        val presetName: TextView = findViewById(R.id.preset_detail_name)
        val presetDescription: TextView = findViewById(R.id.preset_detail_description)
        val removePresetButton: Button = findViewById(R.id.remove_button)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentPresetId = bundle.getLong(PRESET_ID)
        }

        currentPresetId?.let {
            val currentPreset = presetDetailViewModel.getPresetForId(it)
            presetName.text = currentPreset?.name
            presetDescription.text = currentPreset?.description

            removePresetButton.setOnClickListener {
                if (currentPreset != null) {
                    presetDetailViewModel.removePreset(currentPreset)
                }
                finish()
            }

        }
    }

}
package com.groupb1.phonefreedom.presetList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.groupb1.phonefreedom.PresetsAdapter
import com.groupb1.phonefreedom.PresetsListViewModel
import com.groupb1.phonefreedom.PresetsListViewModelFactory
import com.groupb1.phonefreedom.addPreset.AddPresetActivity
import com.groupb1.phonefreedom.presetDetail.PresetDetailActivity
import com.groupb1.phonefreedom.R
import com.groupb1.phonefreedom.addPreset.PRESET_DESCRIPTION
import com.groupb1.phonefreedom.addPreset.PRESET_NAME
import com.groupb1.phonefreedom.data.Preset
/*
const val PRESET_ID = "preset id"

class PresetsListActivity : AppCompatActivity() {
    private val newPresetActivityRequestCode = 1
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


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_list_preset)

        val presetsAdapter = PresetsAdapter { preset -> adapterOnClick(preset)  }
        val recyclerView: RecyclerView = findViewById(R.id.presetList)
        presetsListViewModel.presetsLiveData.observe(this, {
            it?.let {
                presetsAdapter.submitList(it as MutableList<Preset>)

            }
        })

        val actionButton: View = findViewById(R.id.floatingActionButton)
        actionButton.setOnClickListener {
            actionButtonOnClick()
        }
    }

    private fun adapterOnClick(preset: Preset) {
        val intent = Intent(this, PresetDetailActivity()::class.java)
        intent.putExtra(PRESET_ID, preset.id)
        startActivity(intent)
    }

    private fun actionButtonOnClick() {
        val intent = Intent(this, AddPresetActivity::class.java)
        getResult.launch(intent)
    }

}

 */
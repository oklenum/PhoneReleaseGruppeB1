package com.groupb1.phonefreedom.presetDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.groupb1.phonefreedom.data.DataSource
import com.groupb1.phonefreedom.data.Preset
import java.lang.IllegalArgumentException

class PresetDetailViewModel(private val dataSource: DataSource) : ViewModel() {

    fun getPresetForId(id: Long) : Preset? {
        return dataSource.getPresetForId(id)
    }

    fun removePreset(preset: Preset) {
        dataSource.removePreset(preset)
    }
}

class PresetDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PresetDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PresetDetailViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
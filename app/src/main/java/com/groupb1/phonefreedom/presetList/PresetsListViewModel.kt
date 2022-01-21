package com.groupb1.phonefreedom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.groupb1.phonefreedom.data.DataSource
import com.groupb1.phonefreedom.data.Preset
import java.lang.IllegalArgumentException
import kotlin.random.Random

/*
Presets w/ recycler view implemented with inspiration from:
https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView
https://github.com/android/views-widgets-samples
 */

class PresetsListViewModel(val dataSource: DataSource) : ViewModel() {

    val presetsLiveData = dataSource.getPresetList()

    fun insertPreset(presetName: String?, presetDescription: String?) {
        if (presetName == null || presetDescription == null) {
            return
        }

        val newPreset = Preset(
            Random.nextLong(),
            presetName,
            presetDescription
        )

        dataSource.addPreset(newPreset)
    }
}

class PresetsListViewModelFactory(private val context: FirstFragment) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PresetsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PresetsListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.groupb1.phonefreedom.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialPresetList = presetList(resources)
    private val presetsLiveData = MutableLiveData(initialPresetList)

    fun addPreset(preset: Preset) {
        val currentList = presetsLiveData.value
        if (currentList == null) {
            presetsLiveData.postValue(listOf(preset))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, preset)
            presetsLiveData.postValue(updatedList)
        }
    }

    fun removePreset (preset: Preset) {
        val currentList = presetsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(preset)
            presetsLiveData.postValue(updatedList)
        }
    }

    fun getPresetForId(id: Long): Preset? {
        presetsLiveData.value?.let { presets ->
            return presets.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getPresetList(): LiveData<List<Preset>> {
        return presetsLiveData
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
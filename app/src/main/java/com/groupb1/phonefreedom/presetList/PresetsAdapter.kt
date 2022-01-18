package com.groupb1.phonefreedom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.groupb1.phonefreedom.R
import com.groupb1.phonefreedom.data.Preset

class PresetsAdapter(private val onClick: (Preset) -> Unit) :
    ListAdapter<Preset, PresetsAdapter.PresetViewHolder>(PresetDiffCallback) {


    class PresetViewHolder(itemView: View, val onClick: (Preset) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val linLayout: LinearLayout = itemView.findViewById(R.id.lin_layout)
        private val presetButton: Button = itemView.findViewById(R.id.preset_button)
        private val presetSwitch: SwitchMaterial = itemView.findViewById(R.id.preset_switch)
        private var currentPreset: Preset? = null

        init {
            presetButton.setOnClickListener {
                currentPreset?.let {
                    onClick(it)
                }
            }
        }

        /* Bind preset name */
        fun bind(preset: Preset) {
            currentPreset = preset

            presetButton.text = preset.name
        }
    }

    /* Creates and inflates view and return PresetViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.preset_view, parent, false)
        return PresetViewHolder(view, onClick)
    }

    /* Gets current preset and uses it to bind view. */
    override fun onBindViewHolder(holder: PresetViewHolder, position: Int) {
        val preset = getItem(position)
        holder.bind(preset)

    }
}

object PresetDiffCallback : DiffUtil.ItemCallback<Preset>() {
    override fun areItemsTheSame(oldItem: Preset, newItem: Preset): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: Preset, newItem: Preset): Boolean {
        return oldItem.id == newItem.id
    }
}
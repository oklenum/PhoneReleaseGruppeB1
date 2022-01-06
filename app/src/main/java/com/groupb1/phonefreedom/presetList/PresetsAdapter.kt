package com.groupb1.phonefreedom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.groupb1.phonefreedom.R
import com.groupb1.phonefreedom.data.Preset

class PresetsAdapter(private val onClick: (Preset) -> Unit) :
    ListAdapter<Preset, PresetsAdapter.PresetViewHolder>(PresetDiffCallback) {


    class PresetViewHolder(itemView: View, val onClick: (Preset) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val presetTextView: TextView = itemView.findViewById(R.id.preset_button)
        private var currentPreset: Preset? = null

        init {
            itemView.setOnClickListener {
                currentPreset?.let {
                    onClick(it)
                }
            }
        }

        /* Bind preset name */
        fun bind(flower: Preset) {
            currentPreset = flower

            presetTextView.text = flower.name
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
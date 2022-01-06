package com.groupb1.phonefreedom.data

import android.content.res.Resources
import com.groupb1.phonefreedom.R

fun presetList(resources: Resources): List<Preset> {
    return listOf(
        Preset(
            id = 1,
            name = resources.getString(R.string.preset1_name),
            description = resources.getString(R.string.preset1_description)
        ),
        Preset(
            id = 2,
            name = resources.getString(R.string.preset2_name),
            description = resources.getString(R.string.preset2_description)
        ),
        Preset(
            id = 3,
            name = resources.getString(R.string.preset3_name),
            description = resources.getString(R.string.preset3_description)
        ),
        Preset(
            id = 4,
            name = resources.getString(R.string.preset4_name),
            description = resources.getString(R.string.preset4_description)
        ),
        Preset(
            id = 5,
            name = resources.getString(R.string.preset5_name),
            description = resources.getString(R.string.preset5_description)
        ),
        Preset(
            id = 6,
            name = resources.getString(R.string.preset6_name),
            description = resources.getString(R.string.preset6_description)
        )
    )
}
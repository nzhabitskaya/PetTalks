package com.mobile.android.chameapps.pettalks.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Switch


/**
 * Created by Natallia Zhabitskaya on 12/09/2019.
 */

@SuppressLint("ViewConstructor")
class CustomSwitch(context: Context?) : Switch(context) {

    init {
        val prefs: SharedPreferences = context!!.getSharedPreferences(
            "com.mobile.android.chameapps.pettalks", Context.MODE_PRIVATE
        )
        setOnCheckedChangeListener { buttonView, isChecked ->
            prefs.edit().putBoolean(buttonView.id.toString(), isChecked).apply()
        }
    }
}
package com.mobile.android.chameapps.pettalks.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Switch


/**
 * Created by Natallia Zhabitskaya on 12/09/2019.
 */

@SuppressLint("ViewConstructor")
class CustomSwitch(context: Context?) : Switch(context) {

    init {
        setOnCheckedChangeListener { buttonView, isChecked ->
            val msg = if (isChecked) "ON" else "OFF"
            Log.e("ABC", "id: = " + buttonView.id)
            Log.e("ABC", "isChecked = " + msg)

            val prefs: SharedPreferences = context!!.getSharedPreferences(
                "com.mobile.android.chameapps.pettalks", Context.MODE_PRIVATE
            )
            prefs.edit().putBoolean(buttonView.id.toString(), isChecked).apply()
        }
    }
}
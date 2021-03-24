package com.bravedroid.watertracker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PreferencesHelper constructor(context: Context) {
    private val preferences = context.getSharedPreferences("water_tracker_prefs", MODE_PRIVATE)
    private val KEY_WATER_INTAKE = "KEY_WATER_INTAKE"
    private var waterIntakeListener: WaterIntakePreferenceListener? = null
    fun getWaterIntake() = preferences.getInt(KEY_WATER_INTAKE, 0)

    fun incrementWaterTracker() {
        val waterIntake = preferences.getInt(KEY_WATER_INTAKE, 0)
        preferences.edit().putInt(KEY_WATER_INTAKE, waterIntake + 1).apply()
    }

    fun subscribeToWaterIntakeChanged(listener: WaterIntakePreferenceListener) {
        this.waterIntakeListener = listener
        preferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    fun unsubscribeFromWaterIntakeChanged() {
        this.waterIntakeListener = null
        preferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
            if (key == KEY_WATER_INTAKE) {
                waterIntakeListener?.onWaterIntakeChanged(preferences.getInt(key, 0))
            }
        }
}


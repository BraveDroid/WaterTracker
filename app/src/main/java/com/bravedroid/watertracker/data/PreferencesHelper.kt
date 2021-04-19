package com.bravedroid.watertracker.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bravedroid.watertracker.testing.OpenForTesting
import com.bravedroid.watertracker.util.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class PreferencesHelper @Inject constructor(
    val sharedPreferences: SharedPreferences,
   // @ApplicationContext context: Context,
    logger: Logger,
) {
    init {
        logger.log("PreferencesHelper ${this.hashCode()}")
    }

    private var waterIntakeListeners: MutableList<WaterIntakePreferenceListener> = mutableListOf()
    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
            if (key == KEY_WATER_INTAKE) {
                waterIntakeListeners.forEach {
                    it.onWaterIntakeChanged(preferences.getInt(key, 0))
                }
            }
        }

    private fun registerToPreference() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    private fun unregisterFromPreference() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    internal fun getWaterIntake() = sharedPreferences.getInt(KEY_WATER_INTAKE, 0)

    internal fun incrementWaterTracker() {
        val waterIntake = sharedPreferences.getInt(KEY_WATER_INTAKE, 0)
        sharedPreferences.edit().putInt(KEY_WATER_INTAKE, waterIntake + 1).apply()
    }

    internal fun subscribeToWaterIntakeChanged(listener: WaterIntakePreferenceListener) {
        this.waterIntakeListeners.add(listener)
        if (this.waterIntakeListeners.size == 1) {
            registerToPreference()
        }
    }

    internal fun unsubscribeFromWaterIntakeChanged(listener: WaterIntakePreferenceListener) {
        this.waterIntakeListeners.remove(listener)
        if (this.waterIntakeListeners.isEmpty()) {
            unregisterFromPreference()
        }
    }

    interface WaterIntakePreferenceListener {
        fun onWaterIntakeChanged(waterIntake: Int)
    }

    private companion object {
        private const val KEY_WATER_INTAKE = "KEY_WATER_INTAKE"
    }
}

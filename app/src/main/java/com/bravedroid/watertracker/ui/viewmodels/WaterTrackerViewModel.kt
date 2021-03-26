package com.bravedroid.watertracker.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bravedroid.watertracker.data.PreferencesHelper
import com.bravedroid.watertracker.util.Logger

internal class WaterTrackerViewModel @ViewModelInject constructor(
    private val preferencesHelper: PreferencesHelper,
    var logger: Logger,
    @Assisted private val saveStateHandle: SavedStateHandle
) : ViewModel(), PreferencesHelper.WaterIntakePreferenceListener {

    private val _waterIntake = MutableLiveData<Int>()
    internal val waterIntake: LiveData<Int> = _waterIntake

    init {
        logger.log("${this.javaClass.simpleName} ${this.hashCode()}")
        preferencesHelper.subscribeToWaterIntakeChanged(this)
        _waterIntake.postValue(preferencesHelper.getWaterIntake())
    }

    override fun onCleared() {
        preferencesHelper.unsubscribeFromWaterIntakeChanged(this)
        super.onCleared()
    }

    internal fun incrementWaterIntake() {
        preferencesHelper.incrementWaterTracker()
    }

    internal fun retrieveWaterIntake(): Int = preferencesHelper.getWaterIntake()

    override fun onWaterIntakeChanged(waterIntake: Int) {
        _waterIntake.value = waterIntake
    }
}

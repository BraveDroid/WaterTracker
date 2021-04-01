package com.bravedroid.watertracker.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bravedroid.watertracker.data.CalculatorRepository
import com.bravedroid.watertracker.ui.viewmodels.Fragment22ViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val app: WaterTrackerApplication) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            if (isAssignableFrom(Fragment22ViewModel::class.java)) {
                Fragment22ViewModel(CalculatorRepository(app.appCoroutineContext))
            } else {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

@Suppress("UNCHECKED_CAST")
fun Fragment.viewModelFactory(app: WaterTrackerApplication) = ViewModelFactory(app)

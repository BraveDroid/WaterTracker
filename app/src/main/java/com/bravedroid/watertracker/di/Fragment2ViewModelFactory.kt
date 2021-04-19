package com.bravedroid.watertracker.di

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.bravedroid.watertracker.data.CalculatorRepository
import com.bravedroid.watertracker.ui.viewmodels.Fragment22ViewModel
import com.bravedroid.watertracker.util.DispatcherHelper

//@Suppress("UNCHECKED_CAST")
//class ViewModelFactory(private val app: WaterTrackerApplication) : ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//        with(modelClass) {
//            if (isAssignableFrom(Fragment22ViewModel::class.java)) {
//                Fragment22ViewModel(CalculatorRepository(app.appCoroutineContext))
//            } else {
//                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//            }
//        } as T
//}
//
//@Suppress("UNCHECKED_CAST")
//fun Fragment.viewModelFactory(app: WaterTrackerApplication) = ViewModelFactory(app)
//

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val app: WaterTrackerApplication,
    private val owner: SavedStateRegistryOwner,
    private val defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = with(modelClass) {
        if (isAssignableFrom(Fragment22ViewModel::class.java)) {
            Fragment22ViewModel(
                handle,
                CalculatorRepository(app.appCoroutineContext, DispatcherHelper())
            )
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

package com.bravedroid.watertracker.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bravedroid.watertracker.util.Logger
import dagger.hilt.android.scopes.ActivityScoped

class InfoViewModel @ViewModelInject constructor(
    @Assisted private val saveStateHandle: SavedStateHandle,
    var logger: Logger,
) : ViewModel() {

    init {
        logger.log("${this.javaClass.simpleName} ${this.hashCode()}")
    }
}

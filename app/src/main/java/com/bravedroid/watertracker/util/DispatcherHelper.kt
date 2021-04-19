package com.bravedroid.watertracker.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class DispatcherHelper {
    fun provideMain(): CoroutineDispatcher = Dispatchers.Main
    fun provideIo(): CoroutineDispatcher = Dispatchers.IO
    fun provideCpu(): CoroutineDispatcher = Dispatchers.Default
}

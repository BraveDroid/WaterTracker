package com.bravedroid.watertracker.data

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CalculatorRepository(override val coroutineContext: CoroutineContext) : CoroutineScope {
    fun addSync(x: Int, y: Int): Int = x + y

    fun addAsyncCallback(x: Int, y: Int, callback: (Int) -> Unit) {
        launch {
            delay(1_000)
            withContext(Dispatchers.Main) {
                callback.invoke(x + y)
            }
        }
    }

    suspend fun addAsyncSuspend(x: Int, y: Int): Int {
        delay(1_000)
        return x + y
    }
}

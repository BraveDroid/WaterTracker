package com.bravedroid.watertracker.data

import com.bravedroid.watertracker.util.DispatcherHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class CalculatorRepository(
    override val coroutineContext: CoroutineContext,
    private val dispatcherHelper: DispatcherHelper,
) : CoroutineScope {
    fun addSync(x: Int, y: Int): Int = x + y

    fun addAsyncCallback(x: Int, y: Int, callback: (Int) -> Unit) {
        launch {
            delay(1_000)
            withContext(dispatcherHelper.provideMain()) {
                callback.invoke(x + y)
            }
        }
    }

    suspend fun addAsyncSuspend(x: Int, y: Int): Int {
        delay(1_000)
        return x + y
    }
}

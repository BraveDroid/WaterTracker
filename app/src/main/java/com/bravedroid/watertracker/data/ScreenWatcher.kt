package com.bravedroid.watertracker.data

import android.app.Activity
import com.bravedroid.watertracker.util.Logger
import com.bravedroid.watertracker.ui.viewmodels.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreenWatcher @Inject constructor(logger: Logger) {

    init {
        logger.log("${this.javaClass.simpleName} ${this.hashCode()}")
    }

    private val map = mutableMapOf<Class<out Activity>, Int>()
    private val _count: SingleLiveEvent<Map<Class<out Activity>, Int>> =
        SingleLiveEvent()
    internal val totalCount: SingleLiveEvent<Map<Class<out Activity>, Int>> = _count

    fun incrementVisits(activity: Class<out Activity>) {
        val count = map[activity]
        if (count == null) {
            map[activity] = 1
        } else {
            map[activity] = count.inc()
        }
        _count.value = map
    }
}

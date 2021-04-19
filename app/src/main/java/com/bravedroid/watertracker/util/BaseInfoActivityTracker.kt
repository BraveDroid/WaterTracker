package com.bravedroid.watertracker.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.*
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Tracker1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Tracker2

abstract class BaseInfoActivityTracker constructor(
    private val logger: Logger,
    private val timer: Timer,
) : LifecycleObserver, InfoActivityTracker {


    override fun startTracking() {
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    logger.log("START TRACKING :  ${this@BaseInfoActivityTracker.javaClass.simpleName}" + Date())
                }
            },
            0,
            1 * 1000
        )
    }

    override fun finishTracking() {
        timer.cancel()
        logger.log("END TRACKING : ${this.javaClass.simpleName}" + Date())
    }
}

interface InfoActivityTracker {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTracking()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun finishTracking()
}

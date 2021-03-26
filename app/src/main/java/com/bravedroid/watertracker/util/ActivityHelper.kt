package com.bravedroid.watertracker.util

import javax.inject.Inject

class ActivityHelper @Inject constructor(
    private val logger: Logger,
) {
    init {
        logger.log("${this.javaClass.simpleName} ${this.hashCode()}", "logger")
    }
}

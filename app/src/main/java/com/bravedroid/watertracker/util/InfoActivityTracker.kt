package com.bravedroid.watertracker.util

import java.util.*
import javax.inject.Inject

class InfoActivityTracker1 @Inject constructor(
    private val logger: Logger,
    private val timer: Timer = Timer(true),
    ): BaseInfoActivityTracker(logger,timer)

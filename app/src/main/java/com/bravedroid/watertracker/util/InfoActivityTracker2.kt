package com.bravedroid.watertracker.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import javax.inject.Inject

class InfoActivityTracker2 @Inject constructor(
    context: Context,
    logger: Logger,
    timer: Timer = Timer(true)
) : BaseInfoActivityTracker(logger,timer) {
    init {
        (context as AppCompatActivity).lifecycle.addObserver(this)
    }
}

package com.bravedroid.watertracker.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bravedroid.watertracker.data.ScreenWatcher
import com.bravedroid.watertracker.databinding.ActivityInfoBinding
import com.bravedroid.watertracker.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoActivity : AppCompatActivity() {

    @Tracker1
    @Inject
    lateinit var tracker1: BaseInfoActivityTracker

    @Tracker2
    @Inject
    lateinit var tracker2: BaseInfoActivityTracker

    @Inject
    lateinit var screenWatcher: ScreenWatcher

    @Inject
    internal lateinit var logger: Logger

    @Inject
    lateinit var activityHelper: ActivityHelper

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityHelper.hashCode()

        lifecycle.addObserver(tracker1)
    }

    override fun onStart() {
        super.onStart()
        screenWatcher.totalCount.observe(this){ map ->
            map.entries.forEach {
                logger.log("**** from InfoActivity ${it.key} ${it.value}", "Visits")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        screenWatcher.incrementVisits(this::class.java)
    }

    override fun onStop() {
        super.onStop()
        screenWatcher.totalCount.removeObservers(this)
    }
}

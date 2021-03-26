package com.bravedroid.watertracker.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bravedroid.watertracker.data.ScreenWatcher
import com.bravedroid.watertracker.databinding.ActivityWaterTrackerBinding
import com.bravedroid.watertracker.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class WaterTrackerActivity : AppCompatActivity() {

    @Inject
    lateinit var screenWatcher: ScreenWatcher

    @Inject
    internal lateinit var logger: Logger

    @Inject
    lateinit var activityHelper: ActivityHelper

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    @ImageResources
    lateinit var imageResources: Resources

    private lateinit var binding: ActivityWaterTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gotoInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        activityHelper.hashCode()

        imageLoader.setImageViewWithGlide(
            imageResources,
            binding.homeIV,
            "home",
        )
    }

    override fun onStart() {
        super.onStart()
        screenWatcher.totalCount.observe(this) { map ->
            map.entries.forEach {
                logger.log("**** from WaterTrackerActivity ${it.key} ${it.value}", "Visits")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        screenWatcher.incrementVisits(this.javaClass)
    }

    override fun onPause() {
        super.onPause()
        screenWatcher.totalCount.removeObservers(this)
    }
}

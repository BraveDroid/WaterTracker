package com.bravedroid.watertracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bravedroid.watertracker.databinding.ActivityWaterTrackerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WaterTrackerActivity : AppCompatActivity(), WaterIntakePreferenceListener {
    private lateinit var binding: ActivityWaterTrackerBinding

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesHelper.subscribeToWaterIntakeChanged(this)

        binding.waterCountText.text = preferencesHelper.getWaterIntake().toString()

        binding.button.setOnClickListener {
            preferencesHelper.incrementWaterTracker()
        }
    }

    override fun onDestroy() {
        preferencesHelper.unsubscribeFromWaterIntakeChanged()
        super.onDestroy()
    }

    override fun onWaterIntakeChanged(waterIntake: Int) {
        binding.waterCountText.text = waterIntake.toString()
    }
}

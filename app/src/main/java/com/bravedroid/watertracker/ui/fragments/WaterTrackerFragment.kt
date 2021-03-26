package com.bravedroid.watertracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bravedroid.watertracker.util.Logger
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentWaterTrackerBinding
import com.bravedroid.watertracker.ui.viewmodels.WaterTrackerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class WaterTrackerFragment : Fragment(R.layout.fragment_water_tracker) {

    private val viewModel: WaterTrackerViewModel by activityViewModels()
    private var _binding: FragmentWaterTrackerBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var logger: Logger

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWaterTrackerBinding.bind(view)

        viewModel.waterIntake.observe(viewLifecycleOwner) {
            binding.waterCountText.setNiceText(it.toString())
        }

        binding.button.setOnClickListener {
            viewModel.incrementWaterIntake()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

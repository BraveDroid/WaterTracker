package com.bravedroid.watertracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentPanelBinding
import com.bravedroid.watertracker.ui.viewmodels.WaterTrackerViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PanelFragment : Fragment(R.layout.fragment_panel) {
    // this is bad coupling , this class is using  RequestManager which is coming from external lib
    // a better way is to use the wrapper class ImageLoader
    @Inject
    lateinit var glide: RequestManager

    private val viewModel: WaterTrackerViewModel by activityViewModels()
    private var _binding: FragmentPanelBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPanelBinding.bind(view)

        viewModel.waterIntake.observe(viewLifecycleOwner) {
            binding.commentTv.setNiceText(it.toString())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

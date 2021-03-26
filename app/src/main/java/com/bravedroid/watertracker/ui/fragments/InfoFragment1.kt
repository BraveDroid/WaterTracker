package com.bravedroid.watertracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentInfo1Binding
import com.bravedroid.watertracker.ui.viewmodels.InfoViewModel
import com.bravedroid.watertracker.util.ImageLoader
import com.bravedroid.watertracker.util.ImageResources
import com.bravedroid.watertracker.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment1 : Fragment(R.layout.fragment_info1) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    @ImageResources
    lateinit var imageResources: Resources

    private val viewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentInfo1Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInfo1Binding.bind(view)
        viewModel.hashCode()

        imageLoader.setImageViewWithGlide(
            imageResources,
            binding.fragment1Background,
            "fargment1",
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun instance() = InfoFragment1()
    }
}

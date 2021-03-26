package com.bravedroid.watertracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentInfo3Binding
import com.bravedroid.watertracker.ui.viewmodels.InfoViewModel
import com.bravedroid.watertracker.util.ImageLoader
import com.bravedroid.watertracker.util.ImageLoaderImpl
import com.bravedroid.watertracker.util.ImageResources
import com.bravedroid.watertracker.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment3 : Fragment(R.layout.fragment_info3) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    @ImageResources
    lateinit var imageResources: Resources

    private val viewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentInfo3Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInfo3Binding.bind(view)
        viewModel.hashCode()

        imageLoader.setImageViewWithGlide(
            imageResources,
            binding.fragment3Background,
            "fargment3",
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun instance() = InfoFragment3()
    }
}

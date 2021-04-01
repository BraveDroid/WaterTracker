package com.bravedroid.watertracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentInfo22Binding
import com.bravedroid.watertracker.di.WaterTrackerApplication
import com.bravedroid.watertracker.di.viewModelFactory
import com.bravedroid.watertracker.ui.viewmodels.Fragment22ViewModel
import com.bravedroid.watertracker.ui.viewmodels.UiState
import com.bravedroid.watertracker.util.ImageLoader
import com.bravedroid.watertracker.util.ImageResources
import com.bravedroid.watertracker.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment22 : Fragment(R.layout.fragment_info22) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    @ImageResources
    lateinit var imageResources: Resources

    private var _binding: FragmentInfo22Binding? = null
    private val binding get() = _binding!!

    private val viewModel: Fragment22ViewModel by activityViewModels {
        viewModelFactory(requireContext().applicationContext as WaterTrackerApplication)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInfo22Binding.bind(view)

        imageLoader.setImageViewWithGlide(
            imageResources,
            binding.fragment22Background,
            "fargment2",
        )

        binding.calculateBtn.setOnClickListener {
            var x: Int = 0
            var y: Int = 0

            if (binding.editTextX.text.toString().isNotEmpty()) {
                x = binding.editTextX.text.toString().toInt()
            }
            if (binding.editTextY.text.toString().isNotEmpty()) {
                y = binding.editTextY.text.toString().toInt()
            }

            when {
                binding.radioSync.isChecked -> {
                    binding.commentTv.text = viewModel.addSync(x, y).toString()
                }
                binding.radioAsyncCallback.isChecked -> {
                    viewModel.addAsyncCallback(x, y)
                }
                binding.radioAsyncSuspend.isChecked -> {
                    viewModel.addAsyncSuspend(x, y)
                }
            }
        }

        viewModel.sumUiState.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it is UiState.Loading
            binding.commentTv.isVisible = it !is UiState.Loading
            if (it is UiState.Success) {
                binding.commentTv.text = it.sumResult.toString()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = InfoFragment22()
    }
}

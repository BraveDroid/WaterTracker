package com.bravedroid.watertracker.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentInfo2Binding
import com.bravedroid.watertracker.ui.viewmodels.InfoViewModel
import com.bravedroid.watertracker.util.*
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class InfoFragment2 : Fragment(R.layout.fragment_info2) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var heavy: Lazy<Heavy>

    @Inject
    lateinit var logger: Logger

    @Inject
    @ImageResources
    lateinit var imageResources: Resources

    private val viewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentInfo2Binding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        val duration = measureTimeMillis {
            super.onAttach(context)
        }
        logger.log("onAttach ${this.javaClass.simpleName} $duration")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInfo2Binding.bind(view)
        viewModel.hashCode()

        Thread {
            val duration = measureTimeMillis {
                //heavy.get()
            }
            logger.log("getting heavy  ${this.javaClass.simpleName} $duration")
        }.start()

        imageLoader.setImageViewWithGlide(
            imageResources,
            binding.fragment2Background,
            "fargment3",
        )

        binding.navigationBtn.setOnClickListener {
            it.setBackgroundColor(Color.RED)
            findNavController().navigate(InfoFragment2Directions.actionInfoFragment2ToInfoFragment22())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun instance() = InfoFragment2()
    }
}

package com.bravedroid.watertracker.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.databinding.FragmentInfo22Binding
import com.bravedroid.watertracker.di.ViewModelFactory
import com.bravedroid.watertracker.di.WaterTrackerApplication
import com.bravedroid.watertracker.ui.ext.LiveDataExt.combineWith
import com.bravedroid.watertracker.ui.viewmodels.Fragment22ViewModel
import com.bravedroid.watertracker.ui.viewmodels.UiState
import com.bravedroid.watertracker.util.ImageLoader
import com.bravedroid.watertracker.util.ImageResources
import com.bravedroid.watertracker.util.Logger
import com.bravedroid.watertracker.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment22 : Fragment(R.layout.fragment_info22) {
    @Inject
    internal lateinit var logger: Logger

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    @ImageResources
    lateinit var imageResources: Resources

    private var _binding: FragmentInfo22Binding? = null
    private val binding get() = _binding!!

    private val viewModel: Fragment22ViewModel by activityViewModels {
        ViewModelFactory(requireContext().applicationContext as WaterTrackerApplication, this)
    }

    //    private val viewModel: Fragment22ViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInfo22Binding.bind(view)

        imageLoader.setImageViewWithGlide(
            imageResources,
            binding.fragment22Background,
            "fargment2",
        )

        viewModel.resultData.observe(viewLifecycleOwner) {
            binding.commentTv.setText(it.toString())
        }

        val _isEditTextXNotEmptyLiveData = MutableLiveData<Boolean>(false)
        val isEditTextXNotEmptyLiveData = _isEditTextXNotEmptyLiveData.distinctUntilChanged()
        val _isEditTextYNotEmptyLiveData = MutableLiveData<Boolean>(false)
        val isEditTextYNotEmptyLiveData = _isEditTextYNotEmptyLiveData.distinctUntilChanged()
        val _isRadioButtonCheckedLiveData = MutableLiveData<Boolean>(false)
        val isRadioButtonCheckedLiveData = _isRadioButtonCheckedLiveData.distinctUntilChanged()

        binding.editTextX.addTextChangedListener(NotEmptyTextWatcher {
            _isEditTextXNotEmptyLiveData.value = it
        })

        binding.editTextY.addTextChangedListener(NotEmptyTextWatcher {
            _isEditTextYNotEmptyLiveData.value = it
        })

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            _isRadioButtonCheckedLiveData.value = true
        }

        isEditTextXNotEmptyLiveData.combineWith(isEditTextYNotEmptyLiveData) { isEditTextXNotEmpty, isEditTextYNotEmpty ->
            isEditTextXNotEmpty == true && isEditTextYNotEmpty == true
        }.combineWith(isRadioButtonCheckedLiveData) { areEditTextsNotEmpty, isRadioButtonChecked ->
            areEditTextsNotEmpty == true && isRadioButtonChecked == true
        }.distinctUntilChanged()
            .observe(viewLifecycleOwner) {
                binding.calculateBtn.isEnabled = it
            }

        binding.calculateBtn.setOnClickListener {
            val x = binding.editTextX.text.toString().toInt()
            val y = binding.editTextY.text.toString().toInt()
            var result = 0

            when {
                binding.radioSync.isChecked -> {
                    result = viewModel.addSync(x, y)
                    viewModel.setResult(result)
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
                viewModel.setResult(it.sumResult)
                binding.commentTv.text = it.sumResult.toString()
//                binding.commentTv.setText(it.sumResult.toString())
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

class NotEmptyTextWatcher(private val action: (s: Boolean) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
        Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            action.invoke(s.toString().isNotEmpty())
        }
    }
}

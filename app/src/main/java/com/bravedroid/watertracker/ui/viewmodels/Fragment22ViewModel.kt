package com.bravedroid.watertracker.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravedroid.watertracker.data.CalculatorRepository
import kotlinx.coroutines.launch

class Fragment22ViewModel(
    private val calculatorRepository: CalculatorRepository
) : ViewModel() {

    private val _sumUiSate = MutableLiveData<UiState>()
    val sumUiState: LiveData<UiState> = _sumUiSate

    fun addSync(x: Int, y: Int): Int = calculatorRepository.addSync(x, y)

    fun addAsyncCallback(x: Int, y: Int) {
        _sumUiSate.value = UiState.Loading
        calculatorRepository.addAsyncCallback(x, y) {
            _sumUiSate.setValue(UiState.Success(it))
        }
    }

    fun addAsyncSuspend(x: Int, y: Int) {
        _sumUiSate.value = UiState.Loading
        viewModelScope.launch {
            _sumUiSate.value = UiState.Success(calculatorRepository.addAsyncSuspend(x, y))
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    class Success(val sumResult: Int) : UiState()
}

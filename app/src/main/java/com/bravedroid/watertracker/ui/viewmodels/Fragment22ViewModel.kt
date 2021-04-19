package com.bravedroid.watertracker.ui.viewmodels

import androidx.lifecycle.*
import com.bravedroid.watertracker.data.CalculatorRepository
import kotlinx.coroutines.launch

class Fragment22ViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val calculatorRepository: CalculatorRepository
) : ViewModel() {

    val resultData: MutableLiveData<Int> = savedStateHandle.getLiveData<Int>("result1")

    fun setResult(result: Int) {
        savedStateHandle["result1"] = result
    }

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
    data class Success(val sumResult: Int) : UiState()
}

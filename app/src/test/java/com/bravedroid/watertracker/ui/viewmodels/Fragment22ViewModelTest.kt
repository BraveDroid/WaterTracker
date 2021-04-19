package com.bravedroid.watertracker.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.bravedroid.watertracker.data.CalculatorRepository
import com.bravedroid.watertracker.util.DispatcherHelper
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class Fragment22ViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var calculatorRepositoryMock: CalculatorRepository
    private lateinit var sut: Fragment22ViewModel
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var dispatcherHelperMock: DispatcherHelper


    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle()
        calculatorRepositoryMock = mock(CalculatorRepository::class.java)
        dispatcherHelperMock = mock(DispatcherHelper::class.java)
        `when`(dispatcherHelperMock.provideMain()).thenReturn(testDispatcher)
        sut = Fragment22ViewModel(savedStateHandle, calculatorRepositoryMock)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSetResult() {
        val observerMock = mock(Observer::class.java) as Observer<Int>
        sut.resultData.observeForever(observerMock)
        sut.setResult(5)
        verify(observerMock).onChanged(5)
    }

    @Test
    fun testAddSync() {
        `when`(calculatorRepositoryMock.addSync(3, 3)).thenReturn(6)
        val result = sut.addSync(3, 3)
        Truth.assertThat(result).isEqualTo(6)
    }

    @Test
    fun testAddAsyncCallback() = runBlockingTest(testDispatcher) {
        val calculatorRepositoryFake = CalculatorRepository(testDispatcher, dispatcherHelperMock)
        sut = Fragment22ViewModel(savedStateHandle, calculatorRepositoryFake)
        val observerMock = mock(Observer::class.java) as Observer<UiState>
        val argumentCaptor = ArgumentCaptor.forClass(UiState::class.java) as ArgumentCaptor<UiState>
        sut.sumUiState.observeForever(observerMock)

        sut.addAsyncCallback(3, 3)

        testDispatcher.advanceTimeBy(3_000)
        verify(observerMock, times(2)).onChanged(argumentCaptor.capture())
        Truth.assertThat(argumentCaptor.allValues).hasSize(2)
        Truth.assertThat(argumentCaptor.allValues[0]).isEqualTo(UiState.Loading)
        Truth.assertThat(argumentCaptor.allValues[1]).isEqualTo(UiState.Success(6))
    }

    @Test
    fun testAddAsyncSuspend() = runBlockingTest(testDispatcher) {
        val calculatorRepositoryFake = CalculatorRepository(testDispatcher, dispatcherHelperMock)
        sut = Fragment22ViewModel(savedStateHandle, calculatorRepositoryFake)
        val observerMock = mock(Observer::class.java) as Observer<UiState>
        val argumentCaptor = ArgumentCaptor.forClass(UiState::class.java) as ArgumentCaptor<UiState>
        sut.sumUiState.observeForever(observerMock)

        sut.addAsyncSuspend(10, 6)

        testDispatcher.advanceTimeBy(3_000)
        verify(observerMock, times(2)).onChanged(argumentCaptor.capture())
        Truth.assertThat(argumentCaptor.allValues).hasSize(2)
        Truth.assertThat(argumentCaptor.allValues[0]).isEqualTo(UiState.Loading)
        Truth.assertThat(argumentCaptor.allValues[1]).isEqualTo(UiState.Success(16))
    }
}

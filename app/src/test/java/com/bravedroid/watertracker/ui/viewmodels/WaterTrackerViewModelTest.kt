package com.bravedroid.watertracker.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.bravedroid.watertracker.data.PreferencesHelper
import com.bravedroid.watertracker.util.Logger
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class WaterTrackerViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var preferencesHelperMock: PreferencesHelper
    private lateinit var loggerMock: Logger
    private lateinit var saveStateHandleMock: SavedStateHandle
    private lateinit var sut: WaterTrackerViewModel


    @Before
    fun setUp() {
        preferencesHelperMock = mock(PreferencesHelper::class.java)
        loggerMock = mock(Logger::class.java)
        saveStateHandleMock = mock(SavedStateHandle::class.java)
        sut = WaterTrackerViewModel(preferencesHelperMock, loggerMock, saveStateHandleMock)
    }

    @Test
    fun `increment Water In takeWaterTracker test`() {
        sut.incrementWaterIntake()
        verify(preferencesHelperMock).incrementWaterTracker()
    }

    @Test
    fun `retrieve WaterIntake test`() {
        `when`(preferencesHelperMock.getWaterIntake()).thenReturn(10)
        val result = sut.retrieveWaterIntake()
        Truth.assertThat(result).isEqualTo(10)

    }

    @Test
    fun `onWaterIntakeChanged should update sut#waterInTake test`() {
        val observerMock = mock(Observer::class.java) as Observer<Int>
        sut.waterIntake.observeForever(observerMock)
        sut.onWaterIntakeChanged(4)
        verify(observerMock).onChanged(4)
        sut.waterIntake.removeObserver(observerMock)

    }
}

package com.bravedroid.watertracker.data

import android.content.SharedPreferences
import com.bravedroid.watertracker.util.Logger
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class PreferencesHelperTest {
    private lateinit var sharedPreferencesMock: SharedPreferences
    private lateinit var loggerMock: Logger
    private lateinit var sut: PreferencesHelper

    @Before
    fun setUp() {
        sharedPreferencesMock = mock(SharedPreferences::class.java)
        loggerMock = mock(Logger::class.java)
        sut = PreferencesHelper(sharedPreferencesMock, loggerMock)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `testGetWaterIntake$WaterTracker_app`() {
        `when`(sharedPreferencesMock.getInt("KEY_WATER_INTAKE", 0)).thenReturn(13)
        val result = sut.getWaterIntake()
        Truth.assertThat(result).isEqualTo(13)
    }

    @Test
    fun `testIncrementWaterTracker$WaterTracker_app`() {
        `when`(sharedPreferencesMock.getInt("KEY_WATER_INTAKE", 0)).thenReturn(13)
        val editorMock = mock(SharedPreferences.Editor::class.java)
        `when`(sharedPreferencesMock.edit()).thenReturn(editorMock)
        `when`(editorMock.putInt("KEY_WATER_INTAKE", 13 + 1)).thenReturn(editorMock)

        sut.incrementWaterTracker()

        verify(sharedPreferencesMock).getInt("KEY_WATER_INTAKE", 0)
        verify(sharedPreferencesMock).edit()
    }

    @Test
    fun `testSubscribeToWaterIntakeChanged$WaterTracker_app`() {
        val listenerMock = mock(PreferencesHelper.WaterIntakePreferenceListener::class.java)

        sut.subscribeToWaterIntakeChanged(listenerMock)

        verify(sharedPreferencesMock).registerOnSharedPreferenceChangeListener(argThat {
            it is SharedPreferences.OnSharedPreferenceChangeListener
        })
    }

    @Test
    fun `testUnsubscribeFromWaterIntakeChanged$WaterTracker_app`() {
        val listenerMock = mock(PreferencesHelper.WaterIntakePreferenceListener::class.java)

        sut.unsubscribeFromWaterIntakeChanged(listenerMock)

        verify(sharedPreferencesMock).unregisterOnSharedPreferenceChangeListener(argThat {
            it is SharedPreferences.OnSharedPreferenceChangeListener
        })
    }

}

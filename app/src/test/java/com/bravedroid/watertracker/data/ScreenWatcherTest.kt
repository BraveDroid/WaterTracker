package com.bravedroid.watertracker.data

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bravedroid.watertracker.ui.activities.InfoActivity
import com.bravedroid.watertracker.ui.activities.WaterTrackerActivity
import com.bravedroid.watertracker.util.Logger
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class ScreenWatcherTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var loggerMock: Logger
    private lateinit var sut: ScreenWatcher

    @Before
    fun setUp() {
        loggerMock = mock(Logger::class.java)
        sut = ScreenWatcher(loggerMock)
    }

    @Test
    fun `test map size when incrementVisits`() {
        val activity = InfoActivity::class.java
        val observerMock = mock(Observer::class.java) as Observer<Map<Class<out Activity>, Int>>

        sut.incrementVisits(activity)
        sut.totalCount.observeForever(observerMock)

        verify(observerMock).onChanged(argThat {
            it.size == 1
        })
    }

    @Test
    fun `test count of visits when IncrementVisits`() {
        val activity = InfoActivity::class.java
        val observerMock = mock(Observer::class.java) as Observer<Map<Class<out Activity>, Int>>

        sut.incrementVisits(activity)
        sut.totalCount.observeForever(observerMock)

        verify(observerMock).onChanged(argThat {
            it[activity] == 1
        })
    }

    @Test
    fun `test count of visits of multiple activities when IncrementVisits`() {
        val activity1 = InfoActivity::class.java
        val activity2 = WaterTrackerActivity::class.java
        val observerMock = mock(Observer::class.java) as Observer<Map<Class<out Activity>, Int>>

        repeat(3) {
            sut.incrementVisits(activity1)
            sut.incrementVisits(activity2)
        }

        sut.totalCount.observeForever(observerMock)

        verify(observerMock).onChanged(argThat {
            it[activity1] == 3 && it[activity2] == 3
        })
    }

    @Test
    fun `test map using argumentCaptor  when incrementVisits`() {
        val activity = InfoActivity::class.java
        val observerMock = mock(Observer::class.java) as Observer<Map<Class<out Activity>, Int>>
        val argumentCaptor =
            ArgumentCaptor.forClass(Map::class.java)
                    as ArgumentCaptor<Map<Class<out Activity>, Int>>

        sut.incrementVisits(activity)
        sut.totalCount.observeForever(observerMock)

        verify(observerMock).onChanged(argumentCaptor.capture())
        (argumentCaptor.value).let {
            Truth.assertThat(it).isNotEmpty()
            Truth.assertThat(it[activity]).isEqualTo(1)
        }
    }

    @Test
    fun `test map using argumentCaptor of multiple activities when incrementVisits`() {
        val activity1 = InfoActivity::class.java
        val activity2 = WaterTrackerActivity::class.java

        val observerMock = mock(Observer::class.java) as Observer<Map<Class<out Activity>, Int>>
        val argumentCaptor =
            ArgumentCaptor.forClass(Map::class.java)
                    as ArgumentCaptor<Map<Class<out Activity>, Int>>

        repeat(4) {
            sut.incrementVisits(activity1)
            sut.incrementVisits(activity2)
        }
        sut.totalCount.observeForever(observerMock)

        verify(observerMock).onChanged(argumentCaptor.capture())
        (argumentCaptor.value).let {
            Truth.assertThat(it).isNotEmpty()
            Truth.assertThat(it[activity1]).isEqualTo(4)
            Truth.assertThat(it[activity2]).isEqualTo(4)
        }
    }
}

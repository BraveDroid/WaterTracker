package com.bravedroid.watertracker.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*

class InfoActivityTracker2Test {

    private lateinit var sut: InfoActivityTracker2
    private lateinit var appCompatActivityMock: AppCompatActivity
    private lateinit var loggerMock: Logger
    private lateinit var timerMock: Timer

    @Before
    fun setUp() {
        appCompatActivityMock = mock(AppCompatActivity::class.java)
        loggerMock = mock(Logger::class.java)
        timerMock = mock(Timer::class.java)
        `when`(appCompatActivityMock.lifecycle).thenReturn(mock(Lifecycle::class.java))
        sut = InfoActivityTracker2(appCompatActivityMock, loggerMock, timerMock)
        verify(appCompatActivityMock).lifecycle
    }

    @Test
    fun ` startTracking test `() {
        sut.startTracking()
        Mockito.verify(timerMock).schedule(
            Mockito.argThat {
                it is TimerTask
            }, Mockito.eq(0L),
            Mockito.eq(1 * 1000L)
        )
    }

    @Test
    fun ` finishTracking test `() {
        sut.finishTracking()
        Mockito.verify(timerMock).cancel()
    }
}

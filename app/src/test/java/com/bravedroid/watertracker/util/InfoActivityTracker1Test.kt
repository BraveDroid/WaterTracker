package com.bravedroid.watertracker.util

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

class InfoActivityTracker1Test {

    private lateinit var sut: InfoActivityTracker1
    private lateinit var loggerMock: Logger
    private lateinit var timerMock: Timer

    @Before
    fun setUp() {
        loggerMock = mock(Logger::class.java)
        timerMock = mock(Timer::class.java)
        sut = InfoActivityTracker1(loggerMock, timerMock)
    }

    @Test
    fun ` startTracking test `() {
        sut.startTracking()
        verify(timerMock).schedule(
            argThat {
                it is TimerTask
            }, eq(0L),
            eq(1 * 1000L)
        )
    }

    @Test
    fun ` finishTracking test `() {
        sut.finishTracking()
        verify(timerMock).cancel()
    }
}

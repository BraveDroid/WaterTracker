package com.bravedroid.watertracker.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class HeavyTest {

    private lateinit var textResourceMock: Resources
    private lateinit var imageResourceMock: Resources
    private lateinit var loggerMock: Logger
    private lateinit var sut: Heavy

    @Before
    fun setUp() {
        textResourceMock = mock(Resources::class.java)
        imageResourceMock = mock(Resources::class.java)
        loggerMock = mock(Logger::class.java)

    }

    @Test
    fun `interact with dependencies when creation test`() {
        sut = Heavy(textResourceMock, imageResourceMock, loggerMock)
        verify(loggerMock).log(" Heavy start ")
        verify(loggerMock).log(" Heavy finish tasks in init ")
        verify(textResourceMock).get()
        verify(imageResourceMock).get()
    }
}

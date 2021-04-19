package com.bravedroid.watertracker

import com.bravedroid.watertracker.data.CalculatorRepository
import com.bravedroid.watertracker.util.DispatcherHelper
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class CalculatorRepositoryTest {
    private lateinit var dispatcherHelperMock: DispatcherHelper
    private lateinit var sut: CalculatorRepository
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        dispatcherHelperMock = mock(DispatcherHelper::class.java)
        `when`(dispatcherHelperMock.provideMain()).thenReturn(testDispatcher)
        sut = CalculatorRepository(testDispatcher, dispatcherHelperMock)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testAddSync() {
        val result = sut.addSync(5, 5)
        Assert.assertEquals(result, 10)
        Truth.assertThat(result).isEqualTo(10)
    }

    @Test
    fun testAddAsyncCallback() = testDispatcher.runBlockingTest {
        val lock = Job()
        var isCallbackInvoked = false
        val callback: (Int) -> Unit = {
            isCallbackInvoked = true
            Truth.assertThat(isCallbackInvoked).isTrue()
            lock.complete()
        }
       // sut = CalculatorRepository(coroutineContext, dispatcherHelperMock)
        sut.addAsyncCallback(5, 5, callback)
        lock.join()
//        testDispatcher.advanceTimeBy(2000)
    }

    @Test
    fun testAddAsyncSuspend() = runBlockingTest {
        val result = sut.addAsyncSuspend(5, 5)
        // testDispatcher.advanceTimeBy(2000)
        Truth.assertThat(result).isEqualTo(10)
    }
}

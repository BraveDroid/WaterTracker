package com.bravedroid.watertracker.util

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ResourcesTest {

    private lateinit var textResourcesImpl: TextResourcesImpl
    private lateinit var imageResourcesImpl: ImageResourcesImpl

    @Before
    fun setUp() {
        textResourcesImpl = TextResourcesImpl()
        imageResourcesImpl = ImageResourcesImpl()
    }

    @Test
    fun `get map of text resources test`() {
        val result = textResourcesImpl.get()
        Truth.assertThat(result).isNotEmpty()
    }

    @Test
    fun `get map of image resources test`() {
        val result = imageResourcesImpl.get()
        Truth.assertThat(result).isNotEmpty()
    }
}

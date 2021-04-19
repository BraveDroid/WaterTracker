package com.bravedroid.watertracker.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Heavy @Inject constructor(
//    @TextResources private val textResourceMap: Map<String, String>,
//    @ImageResources private val textImagesMap: Map<String, String>,

    @TextResources private val textResource: Resources,
    @ImageResources private val textImages: Resources,
    val logger: Logger,
) {

    var intArray = IntArray(100_000_001)
    var byteArray = ByteArray(1_000_001)

    init {
        logger.log(" Heavy start ")
        for (i in 0..100_000_000) {
            intArray[i]
        }

        for (i in 0..1_000_000) {
            byteArray[i]
        }

        logger.log(" Heavy finish tasks in init ")

        textResource.get().forEach {
            logger.log("${it.key} : ${it.value}")
        }

        textImages.get().forEach {
            logger.log("${it.key} : ${it.value}")
        }
    }
}

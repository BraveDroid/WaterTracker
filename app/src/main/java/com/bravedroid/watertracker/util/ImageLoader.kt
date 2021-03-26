package com.bravedroid.watertracker.util

import android.widget.ImageView
import com.bravedroid.watertracker.testing.OpenForTesting
import com.bumptech.glide.RequestManager

@OpenForTesting
interface ImageLoader {
    fun setImageViewWithGlide(
        imageResources: Resources,
        imageView: ImageView,
        keyOfUrl: String,
    )
}

@OpenForTesting
class ImageLoaderImpl (
    private var glide: RequestManager
) : ImageLoader {
    override fun setImageViewWithGlide(
        imageResources: Resources,
        imageView: ImageView,
        keyOfUrl: String,
    ) {
        with(imageResources.get()
            .entries
            .first { it.key == keyOfUrl }
            .value) {
            glide.load(this)
                .centerCrop()
                .into(imageView)
        }
    }
}

package com.bravedroid.watertracker.ui.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.bravedroid.watertracker.ui.ext.LiveDataExt.combineWith

object LiveDataExt {
    fun <L1, L2, R> LiveData<L1>.combineWith(
        externalSource: LiveData<L2>,
        transform: (L1?, L2?) -> R,
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = transform(this.value, externalSource.value)
        }
        result.addSource(externalSource) {
            result.value = transform(this.value, externalSource.value)
        }
        return result
    }
}

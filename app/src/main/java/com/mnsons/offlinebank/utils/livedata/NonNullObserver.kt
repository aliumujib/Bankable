package com.mnsons.offlinebank.utils.livedata

import androidx.lifecycle.Observer


internal class NonNullObserver<T>(private val block: (T) -> Unit) : Observer<T> {

    override fun onChanged(it: T?) {
        if (it != null) {
            block(it)
        }
    }
}

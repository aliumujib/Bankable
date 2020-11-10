package com.mnsons.offlinebank.utils.ext

/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will throw an [IllegalStateException].
 */
class ViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val viewBindingFactory: (View) -> T,
    private val doBeforeDispose: (T) -> Unit = {}
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    init {
        fragment.lifecycle.addObserver(this)
    }

    private var _value: T? = null

    private val viewLifecycleObserver: DefaultLifecycleObserver =
        object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                disposeValue()
            }
        }

    private fun disposeValue() {
        _value?.let { doBeforeDispose(it) }
        _value = null
    }

    override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer {
            it?.lifecycle?.addObserver(viewLifecycleObserver)
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding: T? = _value
        if (binding != null) {
            return binding
        }

        val lifecycle: Lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException(
                "Should not attempt to get bindings when " +
                    "Fragment views are destroyed."
            )
        }

        return viewBindingFactory(thisRef.requireView()).also {
            _value = it
        }
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T,
    doBeforeDispose: (T) -> Unit = {}
): ViewBindingDelegate<T> =
    ViewBindingDelegate(fragment = this, viewBindingFactory = viewBindingFactory, doBeforeDispose)

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will throw an [IllegalStateException].
 */
class AutoClearedValue<T : Any>(private val fragment: Fragment) : ReadWriteProperty<Fragment, T>,
    DefaultLifecycleObserver {

    init {
        fragment.lifecycle.addObserver(this)
    }

    private var _value: T? = null

    private val viewLifecycleObserver: DefaultLifecycleObserver =
        object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                disposeValue()
            }
        }

    private fun disposeValue() {
        _value = null
    }

    override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer {
            it?.lifecycle?.addObserver(viewLifecycleObserver)
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "should never call auto-cleared-value get when it might not be available"
        )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

fun <T : Any> Fragment.autoDispose(): AutoClearedValue<T> = AutoClearedValue(this)

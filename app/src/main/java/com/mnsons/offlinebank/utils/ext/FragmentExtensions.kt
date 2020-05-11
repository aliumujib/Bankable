/*
 * Copyright 2020 Abdul-Mujeeb Aliu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mnsons.offlinebank.utils.ext

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar

/**
 * Generic view model provider.
 *
 * @param key The key to use to identify the ViewModel.
 * @param factory Function creates a new instance of the ViewModel.
 * @return A ViewModel that is an instance of the given type [VM].
 * @see ViewModel
 */
@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> Fragment.viewModel(
    key: String? = null,
    factory: () -> VM
): VM {
    val factoryViewModel = factory()
    val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factoryViewModel as T
        }
    }

    return if (key != null) {
        ViewModelProviders.of(this, viewModelProviderFactory).get(key, factoryViewModel::class.java)
    } else {
        ViewModelProviders.of(this, viewModelProviderFactory).get(factoryViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> AppCompatActivity.viewModel(
    key: String? = null,
    factory: () -> VM
): VM {
    val factoryViewModel = factory()
    val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factoryViewModel as T
        }
    }

    return if (key != null) {
        ViewModelProviders.of(this, viewModelProviderFactory).get(key, factoryViewModel::class.java)
    } else {
        ViewModelProviders.of(this, viewModelProviderFactory).get(factoryViewModel::class.java)
    }
}

fun Fragment.onBackPressed(block: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                block.invoke()
            }
        }
    )
}

fun Fragment.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_LONG) {
    activity?.let {
        val snack = Snackbar.make(it.findViewById(android.R.id.content), snackbarText, timeLength)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        view.layoutParams = params
        val tv = view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        tv.setTextColor(Color.WHITE)
        snack.show()
    }
}

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
package com.mnsons.offlinebank

import android.app.Application
import android.content.Context
import com.mnsons.offlinebank.di.app.DaggerApplicationComponent
import com.mnsons.offlinebank.di.components.CoreComponent
import com.mnsons.offlinebank.di.components.DaggerCoreComponent
import com.mnsons.offlinebank.di.modules.ContextModule


class ApplicationClass : Application() {

    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()

        initCoreDependencyInjection()
        initAppDependencyInjection()
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initAppDependencyInjection() {
        DaggerApplicationComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {

        /**
         * Obtain core dagger component.
         *
         * @param context The application context
         */
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? ApplicationClass)?.coreComponent
    }

}

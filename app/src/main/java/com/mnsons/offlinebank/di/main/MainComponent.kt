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
package com.mnsons.offlinebank.di.main


import android.content.Context
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.di.components.CoreComponent
import com.mnsons.offlinebank.di.scopes.ActivityScope
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.ui.main.profile.userdetails.UserDetailsFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [MainModule].
 *
 * @see Component
 */
@ActivityScope
@Component(
    modules = [MainModule::class],
    dependencies = [CoreComponent::class])
interface MainComponent {

    fun context(): Context

    fun settingsCache(): SettingsCache

    fun banksCache(): BanksCache

    fun inject(mainActivity: MainActivity)

    fun inject(userDetailsFragment: UserDetailsFragment)

}

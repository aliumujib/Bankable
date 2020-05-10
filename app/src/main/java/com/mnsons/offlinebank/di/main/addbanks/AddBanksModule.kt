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
package com.mnsons.offlinebank.di.main.addbanks

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.di.scopes.ActivityScope
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.home.HomeViewModel
import com.mnsons.offlinebank.ui.main.profile.addbank.AddBankViewModel
import com.mnsons.offlinebank.ui.main.profile.userdetails.UserDetailsFragment
import com.mnsons.offlinebank.utils.ext.viewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [AddBanksComponent].
 *
 * @see Module
 */
@Module
class AddBanksModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: UserDetailsFragment
) {

    @FragmentScope
    @Provides
    fun providesAddBankViewModel(
        settingsCache: SettingsCache,
        banksCache: BanksCache
    ) = fragment.viewModel {
        AddBankViewModel(
            settingsCache,
            banksCache
        )
    }

}

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


import com.mnsons.offlinebank.di.main.MainComponent
import com.mnsons.offlinebank.di.scopes.ActivityScope
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.profile.addbank.AddBankFragment
import com.mnsons.offlinebank.ui.main.profile.userdetails.UserDetailsFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [AddBanksModule].
 *
 * @see Component
 */
@FragmentScope
@Component(
    modules = [AddBanksModule::class],
    dependencies = [MainComponent::class])
interface AddBanksComponent {

    fun inject(addBankFragment: AddBankFragment)

}

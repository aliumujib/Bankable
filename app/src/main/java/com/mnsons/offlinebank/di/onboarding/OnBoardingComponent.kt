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
package com.mnsons.offlinebank.di.onboarding


import com.mnsons.offlinebank.di.components.CoreComponent
import com.mnsons.offlinebank.di.scopes.ActivityScope
import com.mnsons.offlinebank.ui.onboarding.OnBoardingActivity
import com.mnsons.offlinebank.ui.onboarding.collectuserdetails.CollectUserDetailsFragment
import com.mnsons.offlinebank.ui.onboarding.done.AllDoneFragment
import com.mnsons.offlinebank.ui.onboarding.selectuserbanks.SelectUserBanksFragment
import com.mnsons.offlinebank.ui.onboarding.setingup.SettingUpFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [OnBoardingModule].
 *
 * @see Component
 */
@ActivityScope
@Component(
    modules = [OnBoardingModule::class],
    dependencies = [CoreComponent::class])
interface OnBoardingComponent {

    fun inject(onBoardingActivity: OnBoardingActivity)

    fun inject(settingUpFragment: SettingUpFragment)

    fun inject(selectUserBanksFragment: SelectUserBanksFragment)

    fun inject(allDoneFragment: AllDoneFragment)

    fun inject(collectUserDetailsFragment: CollectUserDetailsFragment)

}

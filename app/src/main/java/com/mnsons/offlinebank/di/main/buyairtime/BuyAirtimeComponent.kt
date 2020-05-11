package com.mnsons.offlinebank.di.main.buyairtime

import com.mnsons.offlinebank.di.main.MainComponent
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.buyairtime.BuyAirtimeFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [BuyAirtimeModule].
 *
 * @see Component
 */
@FragmentScope
@Component(
    modules = [BuyAirtimeModule::class],
    dependencies = [MainComponent::class])
interface BuyAirtimeComponent {

    fun inject(buyAirtimeFragment: BuyAirtimeFragment)

}

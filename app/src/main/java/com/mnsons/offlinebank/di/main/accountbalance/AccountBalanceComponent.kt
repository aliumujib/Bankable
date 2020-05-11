package com.mnsons.offlinebank.di.main.accountbalance

import com.mnsons.offlinebank.di.main.MainComponent
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.accountbalance.AccountBalanceFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [AccountBalanceModule].
 *
 * @see Component
 */
@FragmentScope
@Component(
    modules = [AccountBalanceModule::class],
    dependencies = [MainComponent::class])
interface AccountBalanceComponent {

    fun inject(accountBalanceFragment: AccountBalanceFragment)

}

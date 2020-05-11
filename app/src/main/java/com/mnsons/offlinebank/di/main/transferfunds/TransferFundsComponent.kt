package com.mnsons.offlinebank.di.main.transferfunds

import com.mnsons.offlinebank.di.main.MainComponent
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.transfermoney.TransferMoneyFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [TransferFundsModule].
 *
 * @see Component
 */

@FragmentScope
@Component(
    modules = [TransferFundsModule::class],
    dependencies = [MainComponent::class])
interface TransferFundsComponent {

    fun inject(transferMoneyFragment: TransferMoneyFragment)

}

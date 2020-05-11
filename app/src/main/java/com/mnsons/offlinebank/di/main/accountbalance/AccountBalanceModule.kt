package com.mnsons.offlinebank.di.main.accountbalance

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.accountbalance.AccountBalanceFragment
import com.mnsons.offlinebank.ui.main.accountbalance.AccountBalanceViewModel
import com.mnsons.offlinebank.utils.ext.viewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [AccountBalanceComponent].
 *
 * @see Module
 */
@Module
class AccountBalanceModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: AccountBalanceFragment
) {

    @FragmentScope
    @Provides
    fun providesAccountBalanceViewModel() = fragment.viewModel {
        AccountBalanceViewModel()
    }

}

package com.mnsons.offlinebank.di.main.transferfunds

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.TransactionsCache
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.transfermoney.TransferMoneyFragment
import com.mnsons.offlinebank.ui.main.transfermoney.TransferMoneyViewModel
import com.mnsons.offlinebank.utils.ext.viewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [TransferFundsComponent].
 *
 * @see Module
 */
@Module
class TransferFundsModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: TransferMoneyFragment
) {

    @FragmentScope
    @Provides
    fun providesTransferMoneyViewModel(
        banksCache: BanksCache,
        transactionsCache: TransactionsCache
    ) = fragment.viewModel {
        TransferMoneyViewModel(banksCache, transactionsCache)
    }

}

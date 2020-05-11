package com.mnsons.offlinebank.di.main.dashboard

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mnsons.offlinebank.data.cache.impl.TransactionsCache
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.dashboard.DashboardFragment
import com.mnsons.offlinebank.ui.main.dashboard.DashboardViewModel
import com.mnsons.offlinebank.utils.ext.viewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [DashboardComponent].
 *
 * @see Module
 */
@Module
class DashboardModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: DashboardFragment
) {

    @FragmentScope
    @Provides
    fun providesDashboardViewModel(
        transactionsCache: TransactionsCache
    ) = fragment.viewModel {
        DashboardViewModel(transactionsCache)
    }

}

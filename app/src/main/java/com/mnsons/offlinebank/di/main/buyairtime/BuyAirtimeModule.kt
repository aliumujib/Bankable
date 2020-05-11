package com.mnsons.offlinebank.di.main.buyairtime

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mnsons.offlinebank.di.scopes.FragmentScope
import com.mnsons.offlinebank.ui.main.buyairtime.BuyAirtimeFragment
import com.mnsons.offlinebank.ui.main.buyairtime.BuyAirtimeViewModel
import com.mnsons.offlinebank.utils.ext.viewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [BuyAirtimeComponent].
 *
 * @see Module
 */
@Module
class BuyAirtimeModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: BuyAirtimeFragment
) {

    @FragmentScope
    @Provides
    fun providesBuyAirtimeViewModel() = fragment.viewModel {
        BuyAirtimeViewModel()
    }

}

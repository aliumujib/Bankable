package com.mnsons.offlinebank.ui.main.profile.addbank

import androidx.lifecycle.ViewModel
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import javax.inject.Inject

class AddBankViewModel @Inject constructor(
    val banksCache: BanksCache
) : ViewModel() {

}
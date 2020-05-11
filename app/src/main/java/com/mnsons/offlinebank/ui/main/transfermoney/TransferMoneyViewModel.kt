package com.mnsons.offlinebank.ui.main.transfermoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.model.BankMenuModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TransferMoneyViewModel(
    private val bankCache: BanksCache
) : ViewModel() {

    var bankIds = emptyList<BankMenuModel>()

    fun fetchBankMenu(bankId: Int) {
        bankCache.getBankMenu(bankId)
            .onEach {
                bankIds = it
            }.launchIn(viewModelScope)
    }

    fun saveTransaction(){

    }

}
package com.mnsons.offlinebank.ui.main.transfermoney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.model.BankMenuModel
import com.mnsons.offlinebank.model.MoneyTransferModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TransferMoneyViewModel(
    private val bankCache: BanksCache
) : ViewModel() {

    var bankIds = emptyList<BankMenuModel>()

    private val _state = MutableLiveData<TransferMoneyState>()
    val state: LiveData<TransferMoneyState> = _state

    var moneyTransferModel: MoneyTransferModel = MoneyTransferModel()

    fun fetchBankMenu(bankId: Int) {
        bankCache.getBankMenu(bankId)
            .onEach {
                bankIds = it
            }.launchIn(viewModelScope)
    }


    fun initiateFundTransfer(actionId: String, amount: String, accountNumber: String) {
        when {
            amount.isEmpty() -> {
                _state.value = TransferMoneyState.Error(Throwable("Please input an amount to buy"))
            }
            accountNumber.isEmpty() -> {
                _state.value =
                    TransferMoneyState.Error(Throwable("Please input recipient's bank account"))
            }
            else -> {
                moneyTransferModel = moneyTransferModel.copy(
                    actionId = actionId,
                    amount = amount,
                    accountNumber = accountNumber
                )
                _state.value = TransferMoneyState.Initialize(moneyTransferModel)
            }
        }
    }

    fun saveTransaction() {

    }

    fun setRecipientBank(id: Int) {
        moneyTransferModel = moneyTransferModel.copy(recipientBank = id)
    }

}
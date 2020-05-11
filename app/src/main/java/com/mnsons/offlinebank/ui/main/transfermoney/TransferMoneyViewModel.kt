package com.mnsons.offlinebank.ui.main.transfermoney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.TransactionsCache
import com.mnsons.offlinebank.model.BankMenuModel
import com.mnsons.offlinebank.model.MoneyTransferModel
import com.mnsons.offlinebank.model.transaction.TransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.model.transaction.TransactionType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

class TransferMoneyViewModel(
    private val bankCache: BanksCache,
    private val transactionsCache: TransactionsCache
) : ViewModel() {

    var bankIds = emptyList<BankMenuModel>()

    private val _state = MutableLiveData<TransferMoneyState>()
    val state: LiveData<TransferMoneyState> = _state

    private var moneyTransferModel: MoneyTransferModel = MoneyTransferModel()

    fun fetchBankMenu(bankId: Int) {
        bankCache.getBankMenu(bankId)
            .onEach {
                bankIds = it
            }.launchIn(viewModelScope)
    }


    fun persistTransaction(status: TransactionStatus) {
        viewModelScope.launch {
            transactionsCache.saveTransaction(TransactionModel(moneyTransferModel.amount!!.toDouble(),
                Calendar.getInstance().time.time, TransactionType.BANK_TRANSFER,
                status, moneyTransferModel.bank!!))
        }
    }

    fun initiateFundTransfer(actionId: String, amount: String, accountNumber: String, originatingBankName:String) {
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
                    accountNumber = accountNumber,
                    bank = originatingBankName
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
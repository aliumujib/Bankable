package com.mnsons.offlinebank.ui.main.buyairtime

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.TransactionsCache
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.model.buyairtime.BuyAirtimeModel
import com.mnsons.offlinebank.model.transaction.TransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.model.transaction.TransactionType
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class BuyAirtimeViewModel @ViewModelInject constructor(private val transactionsCache: TransactionsCache) :
    ViewModel() {

    lateinit var bank: BankModel

    private var buyAirtimeModel = BuyAirtimeModel()
    private val _state = MutableLiveData<BuyAirtimeState>()
    val state: LiveData<BuyAirtimeState> = _state


    fun persistTransaction(status: TransactionStatus) {
        viewModelScope.launch {
            transactionsCache.saveTransaction(
                TransactionModel(buyAirtimeModel.amount!!.toDouble(), Calendar.getInstance().time.time,
                    TransactionType.AIRTIME_PURCHASE, status, buyAirtimeModel.bank!!)
            )
        }
    }

    fun initiateBuyAirtime(actionId: String, amount: String, phoneNumber: String, originBankAccount:String) {
        when {
            amount.isEmpty() -> {
                _state.value = BuyAirtimeState.Error(Throwable("Please input an amount to buy"))
            }
            phoneNumber.isEmpty() -> {
                _state.value =
                    BuyAirtimeState.Error(Throwable("Please input recipient's phone number"))
            }
            else -> {
                buyAirtimeModel = BuyAirtimeModel(
                    actionId,
                    amount,
                    phoneNumber,
                    originBankAccount
                )
                _state.value = BuyAirtimeState.Initialize(
                    buyAirtimeModel
                )
            }
        }
    }

}
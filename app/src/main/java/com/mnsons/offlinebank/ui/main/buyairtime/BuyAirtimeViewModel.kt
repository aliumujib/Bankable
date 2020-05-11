package com.mnsons.offlinebank.ui.main.buyairtime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.model.BuyAirtimeModel
import javax.inject.Inject

class BuyAirtimeViewModel @Inject constructor() : ViewModel() {

    lateinit var bank: BankModel

    private val _state = MutableLiveData<BuyAirtimeState>()
    val state: LiveData<BuyAirtimeState> = _state

    fun initiateBuyAirtime(actionId: String, amount: String, phoneNumber: String) {
        when {
            amount.isEmpty() -> {
                _state.value = BuyAirtimeState.Error(Throwable("Please input an amount to buy"))
            }
            phoneNumber.isEmpty() -> {
                _state.value =
                    BuyAirtimeState.Error(Throwable("Please input recipient's phone number"))
            }
            else -> {
                _state.value = BuyAirtimeState.Initialize(
                    BuyAirtimeModel(
                        actionId,
                        amount,
                        phoneNumber
                    )
                )
            }
        }
    }

}
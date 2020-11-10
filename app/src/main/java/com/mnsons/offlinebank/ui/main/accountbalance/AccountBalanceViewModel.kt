package com.mnsons.offlinebank.ui.main.accountbalance

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnsons.offlinebank.model.bank.BankModel
import javax.inject.Inject

class AccountBalanceViewModel @ViewModelInject constructor() : ViewModel() {

    lateinit var bank: BankModel

    private val _state = MutableLiveData<AccountBalanceState>()
    val state: LiveData<AccountBalanceState> = _state

    fun fetchAccountBalance() {
        _state.value = AccountBalanceState.Fetching()
    }

}
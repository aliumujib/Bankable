package com.mnsons.offlinebank.ui.main.accountbalance

sealed class AccountBalanceState(
    val isLoading: Boolean,
    val accounts: List<String>?,
    val error: Throwable?
) {

    class Error(error: Throwable?) : AccountBalanceState(false, null, error)
    class Fetching : AccountBalanceState(false, null, null)
    class Fetched(accounts: List<String>?) : AccountBalanceState(false, accounts, null)

}
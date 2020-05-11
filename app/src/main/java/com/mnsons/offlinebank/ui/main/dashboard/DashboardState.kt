package com.mnsons.offlinebank.ui.main.dashboard

import com.mnsons.offlinebank.model.transaction.SectionedTransactionModel

sealed class DashboardState(
    val isLoading: Boolean,
    val transactions: List<SectionedTransactionModel>?,
    val error: Throwable?
) {

    class Error(error: Throwable?) : DashboardState(false, null, error)
    class Editing(transactions: List<SectionedTransactionModel>) :
        DashboardState(false, transactions, null)
    class Idle(transactions: List<SectionedTransactionModel>) :
        DashboardState(false, transactions, null)

}
package com.mnsons.offlinebank.model.transaction

data class SectionedTransactionModel(
    val day: String,
    val transactions: MutableList<TransactionModel>
)
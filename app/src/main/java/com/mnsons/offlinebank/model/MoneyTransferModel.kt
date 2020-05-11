package com.mnsons.offlinebank.model

data class MoneyTransferModel(
    val recipientBank: Int? = null,
    val actionId: String? = null,
    val amount: String? = null,
    val accountNumber: String? = null
)
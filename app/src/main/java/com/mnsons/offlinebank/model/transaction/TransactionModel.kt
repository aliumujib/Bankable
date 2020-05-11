package com.mnsons.offlinebank.model.transaction

data class TransactionModel(
    val amount: Double,
    val timestamp: Long,
    val type: TransactionType,
    val status: TransactionStatus,
    val bank: String
)

enum class TransactionType(val value: String) {
    BANK_TRANSFER("Bank Transfer"),
    AIRTIME_PURCHASE("Airtime Purchase"),
    BALANCE_CHECK("Account Balance Check")
}

enum class TransactionStatus(val value: Int) {
    SUCCESS(1),
    PENDING(2),
    FAILED(3)
}
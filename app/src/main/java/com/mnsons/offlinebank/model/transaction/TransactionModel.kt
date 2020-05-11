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
    BALANCE_CHECK("Account Balance Check");

    companion object {
        @JvmStatic
        fun fromString(category: String): TransactionType =
            values().find { value -> value.value == category } ?: AIRTIME_PURCHASE
    }
}

enum class TransactionStatus(val value: Int) {
    SUCCESS(1),
    PENDING(2),
    FAILED(3);

    companion object {
        @JvmStatic
        fun fromString(status: Int): TransactionStatus =
            values().find { value -> value.value == status } ?: PENDING
    }
}
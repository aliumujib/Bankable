package com.mnsons.offlinebank.model

import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import com.mnsons.offlinebank.data.cache.room.entities.TransactionCacheModel
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.model.transaction.TransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.model.transaction.TransactionType
import java.util.*

fun <I, O> List<I>.mapInto(function: (input: I) -> O): List<O> {
    return map {
        function.invoke(it)
    }
}


fun TransactionModel.toTransactionCacheModel(): TransactionCacheModel {
    return TransactionCacheModel(
        UUID.randomUUID().toString(),
        amount,
        timestamp,
        type.value,
        status.value,
        bank
    )
}


fun TransactionCacheModel.toTransactionModel(): TransactionModel {
    return TransactionModel(
        amount,
        timestamp,
        TransactionType.fromString(type),
        TransactionStatus.fromString(status),
        bank
    )
}

fun BankModel.toBankCacheModel(): BankCacheModel {
    return BankCacheModel(
        id,
        bankName,
        lastKnownBalance,
        sortCode,
        bankLogo
    )
}

fun BankCacheModel.toBankModel(): BankModel {
    return BankModel(
        name,
        id,
        lastKnownBalance,
        sortCode,
        imageURL
    )
}
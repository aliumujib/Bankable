package com.mnsons.offlinebank.model

import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import com.mnsons.offlinebank.data.cache.room.entities.TransactionCacheModel
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.model.transaction.TransactionModel

fun <I, O> List<I>.mapInto(function: (input: I) -> O): List<O> {
    return map {
        function.invoke(it)
    }
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


fun TransactionModel.toTransactionCacheModel(): TransactionCacheModel {
    return TransactionCacheModel(
        id, amount, timestamp, type, status, bank
    )
}

fun TransactionCacheModel.toTransactionModel(): TransactionModel {
    return TransactionModel(
        id, amount, timestamp, type, status, bank
    )
}
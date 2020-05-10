package com.mnsons.offlinebank.model

import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel

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
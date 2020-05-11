package com.mnsons.offlinebank.data.cache.impl

import com.mnsons.offlinebank.data.cache.room.entities.TransactionCacheModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsCache @Inject constructor(

) {

    suspend fun saveBanks(list: List<TransactionCacheModel>) {
    }

    suspend fun saveBank(transaction: TransactionCacheModel) {

    }

    fun getBanks(): Flow<List<TransactionCacheModel>> {
        TODO()
    }

}
package com.mnsons.offlinebank.data.cache.impl

import com.mnsons.offlinebank.data.cache.room.dao.TransactionsDao
import com.mnsons.offlinebank.model.mapInto
import com.mnsons.offlinebank.model.toTransactionCacheModel
import com.mnsons.offlinebank.model.toTransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionsCache @Inject constructor(
    private val transactionsDao: TransactionsDao
) {

    suspend fun saveTransactions(list: List<TransactionModel>) {
        transactionsDao.insert(list.mapInto {
            it.toTransactionCacheModel()
        })
    }

    suspend fun saveTransaction(transaction: TransactionModel) {
        transactionsDao.insert(transaction.toTransactionCacheModel())
    }

    fun getTransaction(): Flow<List<TransactionModel>> {
        return transactionsDao.getAllTransactions().map {
            it.mapInto {
                it.toTransactionModel()
            }
        }
    }

}
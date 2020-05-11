package com.mnsons.offlinebank.data.cache.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.model.transaction.TransactionType

@Entity(tableName = "TRANSACTIONS")
data class TransactionCacheModel(
    @PrimaryKey
    var id: String,
    var amount: Double,
    var timestamp: Long,
    var type: TransactionType,
    var status: TransactionStatus,
    var bank: String
)
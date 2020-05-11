/*
 * Copyright 2020 Abdul-Mujeeb Aliu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mnsons.offlinebank.data.cache.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import com.mnsons.offlinebank.data.cache.room.entities.TransactionCacheModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactions: List<TransactionCacheModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionCacheModel)

    @Query("SELECT * FROM TRANSACTIONS")
    fun getAllTransactions(): Flow<List<TransactionCacheModel>>

    @Query("DELETE FROM TRANSACTIONS WHERE id =:id")
    suspend fun deleteTransactions(id: Int)

    @Query("DELETE FROM TRANSACTIONS")
    suspend fun deleteAllTransactions()

}

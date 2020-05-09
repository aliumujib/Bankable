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

import androidx.room.*
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BanksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(banks: List<BankCacheModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bank: BankCacheModel)

    @Query("SELECT * FROM BANKS")
    fun getAllBanks(): Flow<List<BankCacheModel>>

    @Query("SELECT COUNT(*) FROM BANKS")
    suspend fun getAllBanksCount(): Int

    @Query("DELETE FROM BANKS WHERE id =:id")
    suspend fun deleteBank(id: Int)

    @Query("DELETE FROM BANKS")
    suspend fun deleteAllArticles()

}

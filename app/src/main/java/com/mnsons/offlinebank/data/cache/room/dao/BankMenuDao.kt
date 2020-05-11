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
import com.mnsons.offlinebank.data.cache.room.entities.BankMenuCacheModel

@Dao
interface BankMenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menus: List<BankMenuCacheModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menu: BankMenuCacheModel)

    @Query("SELECT * FROM BANKS_MENU where id=:id")
    suspend fun getBankMenu(id: Int): BankMenuCacheModel?

}

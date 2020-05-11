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
package com.mnsons.offlinebank.data.cache.room


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mnsons.offlinebank.data.cache.room.dao.BankMenuDao
import com.mnsons.offlinebank.data.cache.room.dao.BanksDao
import com.mnsons.offlinebank.data.cache.room.dao.TransactionsDao
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import com.mnsons.offlinebank.data.cache.room.entities.BankMenuCacheModel
import com.mnsons.offlinebank.data.cache.room.entities.TransactionCacheModel


@Database(
    entities = [BankCacheModel::class, BankMenuCacheModel::class, TransactionCacheModel::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DBClass : RoomDatabase() {

    abstract fun banksDao(): BanksDao

    abstract fun bankMenuDao(): BankMenuDao

    abstract fun transactionsDao(): TransactionsDao

}

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
package com.mnsons.offlinebank.di.modules

import android.content.Context
import androidx.room.Room
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.data.cache.room.DBClass
import com.mnsons.offlinebank.data.cache.room.dao.BanksDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CacheModule {


    @Singleton
    @Provides
    fun providesBanksDao(dBClass: DBClass): BanksDao {
        return dBClass.banksDao()
    }

    @Singleton
    @Provides
    fun providesDB(context: Context): DBClass {
        return Room.databaseBuilder(
            context.applicationContext,
            DBClass::class.java, "mandsons_database"
        ).fallbackToDestructiveMigration().build()
    }

}

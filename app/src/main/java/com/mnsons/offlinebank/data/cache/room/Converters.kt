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

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mnsons.offlinebank.model.BankMenuModel


class Converters {

    @TypeConverter
    fun fromBankMenuModelString(value: String?): List<BankMenuModel>? {
        val listType = object : TypeToken<List<BankMenuModel>?>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromBankMenuModelList(data: List<BankMenuModel>?): String {
        val gson = Gson()
        return gson.toJson(data)
    }


}

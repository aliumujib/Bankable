package com.mnsons.offlinebank.data.cache.impl

import com.mnsons.offlinebank.data.cache.room.dao.BanksDao
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BanksCache @Inject constructor(val banksDao: BanksDao) {

    suspend fun saveBanks(list: List<BankCacheModel>) {
        banksDao.insert(list)
    }

    suspend fun saveBank(bank: BankCacheModel) {
        banksDao.insert(bank)
    }

    fun getBanks(): Flow<List<BankCacheModel>> {
        return banksDao.getAllBanks()
    }

    suspend fun isCacheEmpty(): Boolean {
        return banksDao.getAllBanksCount() > 0
    }


}
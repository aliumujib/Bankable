package com.mnsons.offlinebank.data.cache.impl

import com.mnsons.offlinebank.data.cache.room.dao.BankMenuDao
import com.mnsons.offlinebank.data.cache.room.dao.BanksDao
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import com.mnsons.offlinebank.data.cache.room.entities.BankMenuCacheModel
import com.mnsons.offlinebank.model.BankMenuModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BanksCache @Inject constructor(
    private val banksDao: BanksDao,
    private val bankMenuDao: BankMenuDao
) {

    suspend fun saveBanks(list: List<BankCacheModel>) {
        banksDao.insert(list)
    }

    suspend fun saveBank(bank: BankCacheModel) {
        banksDao.insert(bank)
    }

    suspend fun saveBankMenuData(bankId: Int, list: List<BankMenuModel>) {
        var bankMenu = bankMenuDao.getBankMenu(bankId)
        if (bankMenu != null) {
            val menuItems = bankMenu.menuItems.toMutableList()
            menuItems.addAll(list)
            bankMenuDao.insert(bankMenu.copy(menuItems = menuItems))
        } else {
            bankMenu = BankMenuCacheModel(bankId, list)
            bankMenuDao.insert(bankMenu)
        }
    }

    fun getBankMenu(id: Int): Flow<List<BankMenuModel>> {
        return flow {
            emit(bankMenuDao.getBankMenu(id)?.menuItems ?: emptyList())
        }
    }

    fun getBanks(): Flow<List<BankCacheModel>> {
        return banksDao.getAllBanks()
    }

    suspend fun isCacheEmpty(): Boolean {
        return banksDao.getAllBanksCount() > 0
    }

    suspend fun clearBanks() {
        return banksDao.deleteAllBanks()
    }

}
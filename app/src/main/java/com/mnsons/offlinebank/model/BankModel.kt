package com.mnsons.offlinebank.model

import androidx.annotation.DrawableRes
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel

data class BankModel(
    val bankName: Int,
    var id: Int,
    var lastKnownBalance: Long,
    var sortCode: String,
    @DrawableRes
    val bankLogo: Int
)


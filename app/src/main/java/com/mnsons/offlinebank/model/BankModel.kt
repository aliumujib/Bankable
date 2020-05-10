package com.mnsons.offlinebank.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.mnsons.offlinebank.data.cache.room.entities.BankCacheModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BankModel(
    val bankName: Int,
    var id: Int,
    var lastKnownBalance: Long,
    var sortCode: String,
    @DrawableRes
    val bankLogo: Int
) : Parcelable


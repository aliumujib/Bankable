package com.mnsons.offlinebank.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BankModel(
    val bankName: String,
    @DrawableRes val bankLogo: Int? = null
) : Parcelable {

    override fun toString(): String {
        return bankName
    }
}
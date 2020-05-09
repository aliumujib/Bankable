package com.mnsons.offlinebank.model

import androidx.annotation.DrawableRes

data class BankModel(
    val bankName: String,
    @DrawableRes val bankLogo: Int? = null
) {

    override fun toString(): String {
        return bankName
    }
}
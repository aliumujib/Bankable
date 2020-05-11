package com.mnsons.offlinebank.ui.main.buyairtime

import com.mnsons.offlinebank.model.buyairtime.BuyAirtimeModel

sealed class BuyAirtimeState(
    val isLoading: Boolean,
    val buyAirtimeModel: BuyAirtimeModel?,
    val error: Throwable?
) {

    class Error(error: Throwable?) : BuyAirtimeState(false, null, error)
    class Initialize(buyAirtimeModel: BuyAirtimeModel) :
        BuyAirtimeState(false, buyAirtimeModel, null)

}
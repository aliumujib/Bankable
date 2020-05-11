package com.mnsons.offlinebank.ui.main.transfermoney

import com.mnsons.offlinebank.model.MoneyTransferModel

sealed class TransferMoneyState(
    val transferModel: MoneyTransferModel?,
    val error: Throwable?
) {

    class Error(error: Throwable?) : TransferMoneyState(null, error)
    class Initialize(moneyTransferModel: MoneyTransferModel) :
        TransferMoneyState(moneyTransferModel, null)

}
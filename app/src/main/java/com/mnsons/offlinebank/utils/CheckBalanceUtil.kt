package com.mnsons.offlinebank.utils

object CheckBalanceUtil {

    fun getActionIdByBankId(id: Int) = hashMapOfActions[id]

    private val hashMapOfActions = hashMapOf(
        1 to "19142a42"
    )
}


package com.mnsons.offlinebank.utils

object CheckBalanceUtil {

    fun getActionIdByBankId(id: Int): String = hashMapOfActions[id]!!

    private val hashMapOfActions = hashMapOf(
        1 to "19142a42"
    )
}


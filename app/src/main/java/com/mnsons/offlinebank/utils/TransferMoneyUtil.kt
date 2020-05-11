package com.mnsons.offlinebank.utils

object TransferMoneyUtil {

    fun getActionIdByBankId(id: Int): String = hashMapOfActions[id]!!

    private val hashMapOfActions = hashMapOf(
        1 to "75872765",
        2 to "f561a10f"
    )

}


package com.mnsons.offlinebank.utils

object BuyAirtimeUtil {

    fun getActionIdByBankId(id: Int): String = hashMapOfActions[id]!!

    private val hashMapOfActions = hashMapOf(
        1 to "58f263f5"
    )
}


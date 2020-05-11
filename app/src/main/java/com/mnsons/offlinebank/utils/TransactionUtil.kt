package com.mnsons.offlinebank.utils

import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.transaction.SectionedTransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.model.transaction.TransactionType
import java.text.SimpleDateFormat
import java.util.*

object TransactionUtil {

    fun generateDummySectionedTransactions(
        transactions: List<TransactionModel>
    ): List<SectionedTransactionModel> {

        val sectionedTransactions = mutableListOf<SectionedTransactionModel>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val y = transactions.sortedByDescending { it.timestamp }

        y.forEach { transaction ->
            var formattedDateString = dateFormat.format(Date(transaction.timestamp))

            if (formattedDateString == dateFormat.format(Date())) {
                formattedDateString = "Today"
            }

            if (formattedDateString ==
                dateFormat.format(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000))) {
                formattedDateString = "Yesterday"
            }

            val dayList = sectionedTransactions.map { it.day }

            if (formattedDateString in dayList) {
                sectionedTransactions.find {
                    it.day == formattedDateString
                }?.transactions?.add(transaction)
            } else {
                sectionedTransactions.add(
                    SectionedTransactionModel(formattedDateString, mutableListOf(transaction))
                )
            }
        }

        return sectionedTransactions
    }

    val dummyTransactions = listOf(
        TransactionModel(
            5000.0,
            1589200355000,
            TransactionType.BANK_TRANSFER,
            TransactionStatus.SUCCESS,
            "GT Bank"
        ),
        TransactionModel(
            200.0,
            1588595555000,
            TransactionType.AIRTIME_PURCHASE,
            TransactionStatus.SUCCESS,
            "GT Bank"
        ),
        TransactionModel(
            10000.0,
            1589200355000,
            TransactionType.BANK_TRANSFER,
            TransactionStatus.SUCCESS,
            "Access Bank"
        ),
        TransactionModel(
            100.0,
            1589200355000,
            TransactionType.AIRTIME_PURCHASE,
            TransactionStatus.SUCCESS,
            "GT Bank"
        ),
        TransactionModel(
            500.0,
            1589113955000,
            TransactionType.AIRTIME_PURCHASE,
            TransactionStatus.SUCCESS,
            "Access Bank"
        ),
        TransactionModel(
            5000.0,
            1589113955000,
            TransactionType.BANK_TRANSFER,
            TransactionStatus.SUCCESS,
            "GT Bank"
        ),
        TransactionModel(
            12000.0,
            1589113955000,
            TransactionType.BANK_TRANSFER,
            TransactionStatus.SUCCESS,
            "Access Bank"
        ),
        TransactionModel(
            100.0,
            1588595555000,
            TransactionType.AIRTIME_PURCHASE,
            TransactionStatus.SUCCESS,
            "Access Bank"
        ),
        TransactionModel(
            100.0,
            1588595555000,
            TransactionType.AIRTIME_PURCHASE,
            TransactionStatus.SUCCESS,
            "GT Bank"
        )
    )

    fun getIconByType(type: TransactionType) = hashMapOfTransactionIcons[type]!!

    private val hashMapOfTransactionIcons = hashMapOf(
        TransactionType.AIRTIME_PURCHASE to R.drawable.ic_airtime_purchase_icon,
        TransactionType.BANK_TRANSFER to R.drawable.ic_bank_transfer_icon
    )
}
package com.mnsons.offlinebank.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.MoneyTransferModel
import java.security.InvalidParameterException


class MoneyTransferContract : ActivityResultContract<MoneyTransferModel, USSDResult<Unit>>() {

    private lateinit var context: Context

    override fun createIntent(context: Context, input: MoneyTransferModel?): Intent {
        this.context = context
        input?.let {
            return HoverParameters.Builder(context)
                .request(it.actionId)
                .extra("amount", it.amount)
                .extra("bankAccountNumber", it.accountNumber)
                .extra("recipientBank", it.recipientBank.toString())
                .style(R.style.Theme_Hover)
                .buildIntent()
        } ?: throw InvalidParameterException()
    }

    override fun parseResult(resultCode: Int, intent: Intent?): USSDResult<Unit> {
        if (resultCode != Activity.RESULT_OK) return USSDResult(context.getString(R.string.transaction_n0t_successful))
        if (intent == null) return USSDResult(context.getString(R.string.transaction_n0t_successful))

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        sessionTextArr.forEach {
            Log.d(MoneyTransferContract::class.java.simpleName, it)
        }
        Log.d(MoneyTransferContract::class.java.simpleName, intent.toString())

        return if (sessionTextArr.isNotEmpty()) {
            return parseSessionMessage(sessionTextArr.last())
        } else {
            USSDResult(context.getString(R.string.transaction_n0t_successful))
        }
    }


    private fun parseSessionMessage(text: String): USSDResult<Unit> {
        return when {
            text.contains("successful") or text.contains("beneficiary") or text.contains("success") -> {
                USSDResult(context.getString(R.string.transaction_successful), Unit)
            }
            text.contains("No Account is funded") -> {
                USSDResult(context.getString(R.string.transaction_n0t_successful))
            }
            else -> {
                USSDResult(context.getString(R.string.error_index_))
            }
        }
    }

}
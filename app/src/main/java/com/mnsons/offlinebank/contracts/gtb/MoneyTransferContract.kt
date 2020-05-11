package com.mnsons.offlinebank.contracts.gtb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R


class MoneyTransferContract : ActivityResultContract<Unit, String>() {

    private lateinit var context: Context

    override fun createIntent(context: Context, input: Unit?): Intent {
        this.context = context
        return HoverParameters.Builder(context)
            .request("75872765")
            .extra("amount", "200")
            .extra("bankAccountNumber", "0040768684")
            .extra("recipientBank", "1")
            .buildIntent()
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        if (resultCode != Activity.RESULT_OK) return context.getString(R.string.error_fetching_bank_acc_balance)
        if (intent == null) return context.getString(R.string.error_fetching_bank_acc_balance);

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        sessionTextArr.forEach {
            Log.d(MoneyTransferContract::class.java.simpleName, it)
        }
        Log.d(MoneyTransferContract::class.java.simpleName, intent.toString())

        return if (sessionTextArr.isNotEmpty()) {
            sessionTextArr.last()
        } else {
            context.getString(R.string.error_fetching_bank_acc_balance)
        }
    }

}
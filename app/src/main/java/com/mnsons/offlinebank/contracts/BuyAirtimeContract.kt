package com.mnsons.offlinebank.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.buyairtime.BuyAirtimeModel
import com.mnsons.offlinebank.utils.Constants
import java.security.InvalidParameterException

class BuyAirtimeContract : ActivityResultContract<BuyAirtimeModel, USSDResult<Unit>>() {

    private lateinit var context: Context

    override fun createIntent(context: Context, input: BuyAirtimeModel?): Intent {
        this.context = context

        input?.let {
            return HoverParameters.Builder(context)
                .request(it.actionId)
                .extra(Constants.EXTRA_AMOUNT, it.amount)
                .extra(Constants.EXTRA_PHONE_NUMBER, it.phoneNumber)
                .buildIntent()
        } ?: throw InvalidParameterException("Please enter the correct parameters")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): USSDResult<Unit> {
        if (resultCode != Activity.RESULT_OK) return USSDResult(context.getString(R.string.error_index_))
        if (intent == null) return USSDResult(context.getString(R.string.error_index_))

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        sessionTextArr.forEach {
            Log.d(javaClass.simpleName, it)
        }
        Log.d(javaClass.simpleName, intent.toString())

        return if (sessionTextArr.isNotEmpty()) {
            return parseSessionMessage(sessionTextArr.last())
        } else {
            USSDResult(context.getString(R.string.error_index_))
        }
    }

    private fun parseSessionMessage(text: String): USSDResult<Unit> {
        return when {
            text.contains("processing", ignoreCase = true) or text.contains(
                "successful",
                ignoreCase = true
            ) -> {
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
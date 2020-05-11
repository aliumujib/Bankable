package com.mnsons.offlinebank.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BuyAirtimeModel
import com.mnsons.offlinebank.utils.Constants
import java.security.InvalidParameterException

class BuyAirtimeContract : ActivityResultContract<BuyAirtimeModel, String>() {

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

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        if (resultCode != Activity.RESULT_OK) return context.getString(R.string.error_fetching_gtbank_acc_balance)
        if (intent == null) return context.getString(R.string.error_fetching_gtbank_acc_balance);

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        sessionTextArr.forEach {
            Log.d(javaClass.simpleName, it)
        }
        Log.d(javaClass.simpleName, intent.toString())

        return if (sessionTextArr.isNotEmpty()) {
            sessionTextArr.last()
        } else {
            context.getString(R.string.error_fetching_gtbank_acc_balance)
        }
    }

}
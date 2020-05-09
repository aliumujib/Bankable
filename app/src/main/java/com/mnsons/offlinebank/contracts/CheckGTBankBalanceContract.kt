package com.mnsons.offlinebank.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.base.BaseStringOnlyInputContract
import java.security.InvalidParameterException


class CheckGTBankBalanceContract : ActivityResultContract<String,String>() {

    private lateinit var context: Context
    override fun createIntent(context: Context, input: String?): Intent {
        this.context = context
        input?.let {
            return HoverParameters.Builder(context)
                .request(it)
                .buildIntent()
        } ?: throw InvalidParameterException("Please enter the correct parameters")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        if (resultCode != Activity.RESULT_OK) return context.getString(R.string.error_fetching_gtbank_acc_balance)
        if (intent == null) return context.getString(R.string.error_fetching_gtbank_acc_balance);

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        return if (sessionTextArr.isNotEmpty()) {
            sessionTextArr[0]
        } else {
            context.getString(R.string.error_fetching_gtbank_acc_balance)
        }
    }

}
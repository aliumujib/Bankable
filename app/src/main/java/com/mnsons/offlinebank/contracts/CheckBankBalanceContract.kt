package com.mnsons.offlinebank.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R
import java.security.InvalidParameterException

class CheckBankBalanceContract : ActivityResultContract<String, String>() {

    private lateinit var context: Context

    override fun createIntent(context: Context, input: String?): Intent {
        this.context = context
        input?.let {
            return HoverParameters.Builder(context)
                .request(it)
                .style(R.style.Theme_Hover)
                .buildIntent()
        } ?: throw InvalidParameterException("Please enter the correct parameters")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        if (resultCode != Activity.RESULT_OK) return context.getString(R.string.error_index_)
        if (intent == null) return context.getString(R.string.error_index_);

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        sessionTextArr.forEach {
            Log.d(CheckBankBalanceContract::class.java.simpleName, it)
        }
        Log.d(CheckBankBalanceContract::class.java.simpleName, intent.toString())

        return if (sessionTextArr.isNotEmpty()) {
            sessionTextArr.last()
        } else {
            context.getString(R.string.error_index_)
        }
    }

}
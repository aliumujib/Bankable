package com.mnsons.offlinebank.contracts.access

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.USSDResult
import com.mnsons.offlinebank.model.BankMenuModel

class FetchAccessBankOtherBanksContract :
    ActivityResultContract<Unit, USSDResult<List<BankMenuModel>>>() {

    private lateinit var context: Context

    override fun createIntent(context: Context, input: Unit?): Intent {
        this.context = context
        return HoverParameters.Builder(context)
            .request("51dfe430")
            .buildIntent()
    }

    override fun parseResult(resultCode: Int, intent: Intent?): USSDResult<List<BankMenuModel>> {
        if (resultCode != Activity.RESULT_OK) return USSDResult(context.getString(R.string.error_fetching_))
        if (intent == null) return USSDResult(context.getString(R.string.error_fetching_))

        val sessionTextArr: Array<String> =
            intent.getStringArrayExtra("session_messages") ?: emptyArray()
        return if (sessionTextArr.isNotEmpty()) {
            return parseResult(sessionTextArr.last())
        } else {
            USSDResult(context.getString(R.string.error_fetching_bank_acc_balance))
        }
    }

    private fun parseResult(result: String): USSDResult<List<BankMenuModel>> {
        val list = mutableListOf<BankMenuModel>()
        result.lines().forEach {
            //Could be improved with a regex to add only strings that match gtbanks menu style
            if ((it.contains("more", true) or it.contains("Please", true)).not()) {
                val data = it.split(">")
                if(data.size > 1){
                    list.add(BankMenuModel(data[1], data[0].toInt()))
                }
            }
        }
        return USSDResult(data = list)
    }

}
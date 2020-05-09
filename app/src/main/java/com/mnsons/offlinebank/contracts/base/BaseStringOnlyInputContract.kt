package com.mnsons.offlinebank.contracts.base

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.hover.sdk.api.HoverParameters
import java.security.InvalidParameterException

abstract class BaseStringOnlyInputContract<T> : ActivityResultContract<String, T>() {

    override fun createIntent(context: Context, input: String?): Intent {
        input?.let {
            return HoverParameters.Builder(context)
                .request(it)
                .buildIntent()
        } ?: throw InvalidParameterException("Please enter the correct parameters")
    }

}
package com.mnsons.offlinebank.contracts

data class USSDResult<T>(
    val error: String? = null,
    var data: T? = null
)

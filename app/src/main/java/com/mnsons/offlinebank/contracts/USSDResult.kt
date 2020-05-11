package com.mnsons.offlinebank.contracts

data class USSDResult<T>(
    val message: String? = null,
    var data: T? = null
)

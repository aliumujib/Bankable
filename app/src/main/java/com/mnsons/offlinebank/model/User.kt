package com.mnsons.offlinebank.model

data class User(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val banks: List<BankModel>
)
package com.mnsons.offlinebank.model.user

import com.mnsons.offlinebank.model.bank.BankModel

data class UserModel(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val banks: List<BankModel>
)
package com.mnsons.offlinebank.ui.main.profile.addbank

import com.mnsons.offlinebank.model.User

sealed class AddBankState(
    val isLoading: Boolean,
    val user: User?,
    val error: Throwable?
) {

    class Idle(user: User) : AddBankState(false, user, null)
    class Editing(user: User) : AddBankState(false, user, null)
    class Error(error: Throwable?) : AddBankState(false, null, error)

}
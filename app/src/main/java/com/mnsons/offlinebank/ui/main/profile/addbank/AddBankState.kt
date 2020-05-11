package com.mnsons.offlinebank.ui.main.profile.addbank

import com.mnsons.offlinebank.model.user.UserModel

sealed class AddBankState(
    val isLoading: Boolean,
    val user: UserModel?,
    val error: Throwable?
) {

    class Idle(user: UserModel) : AddBankState(false, user, null)
    class Editing(user: UserModel) : AddBankState(false, user, null)
    class Error(error: Throwable?) : AddBankState(false, null, error)

}
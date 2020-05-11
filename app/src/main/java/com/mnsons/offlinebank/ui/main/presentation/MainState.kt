package com.mnsons.offlinebank.ui.main.presentation

import com.mnsons.offlinebank.model.user.UserModel

sealed class MainState(
    val isLoading: Boolean,
    val user: UserModel?,
    val error: Throwable?
) {

    object LoggedOut : MainState(false, null, null)
    class Idle(user: UserModel) : MainState(false, user, null)
    class Editing(user: UserModel) : MainState(false, user, null)
    class Error(error: Throwable?) : MainState(false, null, error)

}
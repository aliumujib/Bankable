package com.mnsons.offlinebank.ui.main.presentation

import com.mnsons.offlinebank.model.User

sealed class MainState(
    val isLoading: Boolean,
    val user: User?,
    val error: Throwable?
) {
    object LoggedOut : MainState(false, null, null)
    class Idle(user: User) : MainState(false, user, null)

}
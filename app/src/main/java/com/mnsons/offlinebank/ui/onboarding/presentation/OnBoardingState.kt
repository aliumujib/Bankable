package com.mnsons.offlinebank.ui.onboarding.presentation

import com.mnsons.offlinebank.model.user.UserModel

sealed class OnBoardingState(
    val isLoading: Boolean,
    val user: UserModel?,
    val error: Throwable?
) {
    class Editing(user: UserModel) : OnBoardingState(false, user, null)
    class Error(error: Throwable?) : OnBoardingState(false, null, error)
    class Loading(isLoading: Boolean) : OnBoardingState(isLoading, null, null)
    class Finished(user: UserModel) : OnBoardingState(false, user, null)

}
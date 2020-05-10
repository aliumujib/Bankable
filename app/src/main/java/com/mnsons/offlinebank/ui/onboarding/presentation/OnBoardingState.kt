package com.mnsons.offlinebank.ui.onboarding.presentation

import com.mnsons.offlinebank.model.User

sealed class OnBoardingState(
    val isLoading: Boolean,
    val user: User?,
    val error: Throwable?
) {
    class Editing(user: User) : OnBoardingState(false, user, null)
    class Error(error: Throwable?) : OnBoardingState(false, null, error)
    class Loading(isLoading: Boolean) : OnBoardingState(isLoading, null, null)
    class Finished(user: User) : OnBoardingState(false, user, null)

}
package com.mnsons.offlinebank.ui.onboarding.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.model.User
import com.mnsons.offlinebank.model.mapInto
import com.mnsons.offlinebank.model.toBankCacheModel
import com.mnsons.offlinebank.ui.commons.banks.BanksPopulator
import kotlinx.coroutines.launch

class OnBoardingViewModel constructor(
    val settingsCache: SettingsCache,
    val banksCache: BanksCache
) : ViewModel() {

    private val _state = MutableLiveData<OnBoardingState>()
    val state = _state

    val banks = BanksPopulator.fetchSupportedBanks()

    private var user: User? = null

    fun setUserDetails(firstName: String, lastName: String, phoneNumber: String) {
        when {
            firstName.isEmpty() -> {
                _state.value = OnBoardingState.Error(Throwable("Please input your first name"))
            }
            lastName.isEmpty() -> {
                _state.value = OnBoardingState.Error(Throwable("Please input your last name"))
            }
            phoneNumber.isEmpty() -> {
                _state.value = OnBoardingState.Error(Throwable("Please input your phone number"))
            }
            else -> {
                user = User(firstName, lastName, phoneNumber, emptyList())
                user?.let {
                    _state.value = OnBoardingState.Editing(it)
                }
            }
        }
    }

    fun setUserBanks(selectedBanks: List<BankModel>) {
        when {
            selectedBanks.isEmpty() -> {
                _state.value = OnBoardingState.Error(Throwable("Please select at least one bank"))
            }
            else -> {
                user = user?.copy(banks = selectedBanks)
                user?.let {
                    _state.value = OnBoardingState.Finished(it)
                }
            }
        }
    }

    fun finishSetup() {
        user?.let {
            viewModelScope.launch {
                settingsCache.setUserFirstName(it.firstName)
                settingsCache.setUserLastName(it.lastName)
                settingsCache.setUserPhone(it.phoneNumber)
                banksCache.saveBanks(it.banks.mapInto {
                    it.toBankCacheModel()
                })
            }
        }
    }


}
package com.mnsons.offlinebank.ui.main.profile.addbank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.model.User
import com.mnsons.offlinebank.model.mapInto
import com.mnsons.offlinebank.model.toBankCacheModel
import com.mnsons.offlinebank.ui.main.presentation.MainState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddBankViewModel @Inject constructor(
    val settingsCache: SettingsCache,
    val banksCache: BanksCache
) : ViewModel() {

    private val _state = MutableLiveData<AddBankState>()
    val state: LiveData<AddBankState> = _state

    fun updateUserBanks(banks: List<BankModel>) {
        if (banks.isEmpty()) {
            _state.value = AddBankState.Error(Throwable("Please select at least one bank"))
        } else {
            if (settingsCache.userDataExists()) {
                _state.value = AddBankState.Editing(
                    User(
                        settingsCache.fetchUserFirstName()!!,
                        settingsCache.fetchUserLastName()!!,
                        settingsCache.fetchUserPhone()!!,
                        banks
                    )
                )

                viewModelScope.launch {
                    banksCache.clearBanks()
                    banksCache.saveBanks(
                        banks.mapInto {
                            it.toBankCacheModel()
                        }
                    )
                }
            } else {
                _state.value = AddBankState.Error(Throwable("There was an error adding new banks"))
            }
        }
    }
}
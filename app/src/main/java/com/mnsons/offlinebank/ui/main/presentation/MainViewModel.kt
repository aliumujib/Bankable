package com.mnsons.offlinebank.ui.main.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.model.*
import com.mnsons.offlinebank.model.user.UserModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel @ViewModelInject constructor(
    val settingsCache: SettingsCache,
    val banksCache: BanksCache
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state

    init {
        if (settingsCache.userDataExists()) {
            banksCache.getBanks()
                .onEach { banks ->
                    _state.value = MainState.Idle(
                        UserModel(
                            settingsCache.fetchUserFirstName()!!,
                            settingsCache.fetchUserLastName()!!,
                            settingsCache.fetchUserPhone()!!,
                            banks.mapInto {
                                it.toBankModel()
                            }
                        )
                    )
                }
                .launchIn(viewModelScope)
        } else {
            _state.value = MainState.LoggedOut
        }
    }


}
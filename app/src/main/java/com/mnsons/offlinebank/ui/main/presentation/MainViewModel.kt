package com.mnsons.offlinebank.ui.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.BanksCache
import com.mnsons.offlinebank.data.cache.impl.SettingsCache
import com.mnsons.offlinebank.model.User
import com.mnsons.offlinebank.model.mapInto
import com.mnsons.offlinebank.model.toBankModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val settingsCache: SettingsCache,
    val banksCache: BanksCache
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state


    init {
        if (settingsCache.userDataExists()) {
            banksCache.getBanks()
                .onEach {
                    _state.value = MainState.Idle(
                        User(
                            settingsCache.fetchUserFirstName()!!,
                            settingsCache.fetchUserLastName()!!,
                            settingsCache.fetchUserPhone()!!,
                            it.mapInto {
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
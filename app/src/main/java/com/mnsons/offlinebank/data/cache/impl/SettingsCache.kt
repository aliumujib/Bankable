package com.mnsons.offlinebank.data.cache.impl

import android.content.Context
import javax.inject.Inject

class SettingsCache @Inject constructor(val context: Context) : CoreSharedPrefManager(context) {

    fun fetchUserName(): String {
        return getPref(USER_NAME)
    }


    fun setUserName(userName: String) {
        savePref(USER_NAME, userName)
    }

    companion object {
        private val USER_NAME = "USER_NAME"
    }
}
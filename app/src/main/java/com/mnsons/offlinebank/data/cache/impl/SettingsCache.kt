package com.mnsons.offlinebank.data.cache.impl

import android.content.Context
import javax.inject.Inject

class SettingsCache @Inject constructor(val context: Context) : CoreSharedPrefManager(context) {

    fun fetchUserFirstName(): String? {
        return getPref(USER_FIRST_NAME)
    }

    fun fetchUserLastName(): String? {
        return getPref(USER_LAST_NAME)
    }

    fun fetchUserPhone(): String? {
        return getPref(USER_PHONE)
    }

    fun setUserPhone(phone: String) {
        savePref(USER_PHONE, phone)
    }

    fun setUserLastName(userName: String) {
        savePref(USER_LAST_NAME, userName)
    }

    fun setUserFirstName(userName: String) {
        savePref(USER_FIRST_NAME, userName)
    }

    fun userDataExists():Boolean{
        return fetchUserFirstName() != null && fetchUserLastName()!=null && fetchUserPhone()!=null
    }

    companion object {
        private val USER_FIRST_NAME = "USER_FIRST_NAME"
        private val USER_LAST_NAME = "USER_LAST_NAME"
        private val USER_PHONE = "USER_PHONE"
    }
}
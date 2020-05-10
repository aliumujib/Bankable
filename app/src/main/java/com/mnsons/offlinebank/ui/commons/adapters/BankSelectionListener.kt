package com.mnsons.offlinebank.ui.commons.adapters

interface BankSelectionListener<T> {

    fun select(item: T)

    fun deselect(item: T)

}
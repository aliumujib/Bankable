package com.mnsons.offlinebank.ui.adapters

interface BankSelectionListener<T> {

    fun select(item: T)

    fun deselect(item: T)

}
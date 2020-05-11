package com.mnsons.offlinebank.ui.commons.adapters.bank

interface BankSelectionListener<T> {

    fun select(item: T)

    fun deselect(item: T)

}
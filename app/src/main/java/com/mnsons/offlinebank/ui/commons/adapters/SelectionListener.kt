package com.mnsons.offlinebank.ui.commons.adapters

interface SelectionListener<T> {

    fun select(item: T)

    fun deselect(item: T)

}
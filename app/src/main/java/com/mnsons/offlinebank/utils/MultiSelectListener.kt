package com.mnsons.offlinebank.utils

interface MultiSelectListener<T> {

    fun select(item: T)

    fun deselect(item: T)

}
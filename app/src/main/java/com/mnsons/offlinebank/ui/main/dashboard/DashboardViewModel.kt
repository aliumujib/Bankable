package com.mnsons.offlinebank.ui.main.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnsons.offlinebank.data.cache.impl.TransactionsCache
import com.mnsons.offlinebank.model.transaction.SectionedTransactionModel
import com.mnsons.offlinebank.model.transaction.TransactionModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DashboardViewModel @ViewModelInject constructor(
    transactionsCache: TransactionsCache
) : ViewModel() {

    private val _state = MutableLiveData<DashboardState>()
    val state: LiveData<DashboardState> = _state

    init {
        transactionsCache.getTransaction().onEach {
            _state.value = DashboardState.Idle(generateSectionedTransactions(it))
        }.launchIn(viewModelScope)
    }

    private fun generateSectionedTransactions(
        transactions: List<TransactionModel>
    ): List<SectionedTransactionModel> {
        val sectionedTransactions = mutableListOf<SectionedTransactionModel>()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val sortedTransactions = transactions.sortedByDescending { it.timestamp }

        sortedTransactions.forEach { transaction ->
            val dayString =
                when (val formattedDateString = dateFormat.format(Date(transaction.timestamp))) {
                    dateFormat.format(Date()) -> "Today"
                    dateFormat.format(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)) -> "Yesterday"
                    else -> formattedDateString
                }

            val dayList = sectionedTransactions.map { it.day }

            if (dayString in dayList) {
                sectionedTransactions.find {
                    it.day == dayString
                }?.transactions?.add(transaction)
            } else {
                sectionedTransactions.add(
                    SectionedTransactionModel(dayString, mutableListOf(transaction))
                )
            }
        }

        return sectionedTransactions
    }

}
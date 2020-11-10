package com.mnsons.offlinebank.ui.main.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentDashboardBinding
import com.mnsons.offlinebank.model.transaction.TransactionType
import com.mnsons.offlinebank.ui.commons.adapters.transaction.SectionedTransactionsAdapter
import com.mnsons.offlinebank.ui.commons.adapters.transaction.TransactionsAdapter
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.showSnackbar
import com.mnsons.offlinebank.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private val transactionsAdapter by lazy { TransactionsAdapter() }

    private val sectionedTransactionsAdapter by lazy {
        SectionedTransactionsAdapter(transactionsAdapter)
    }

    private val binding: FragmentDashboardBinding by viewBinding(FragmentDashboardBinding::bind)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSectionedTransactions.adapter = sectionedTransactionsAdapter

        nonNullObserve(dashboardViewModel.state, ::handleStates)
    }

    @SuppressLint("SetTextI18n")
    private fun handleStates(dashboardState: DashboardState) {
        when (dashboardState) {
            is DashboardState.Idle, is DashboardState.Editing -> {
                dashboardState.transactions?.let {
                    sectionedTransactionsAdapter.setTransactions(it)

                    val allTransactions = it.flatMap { it.transactions }

                    binding.tvBankTransferAmount.text = "N " + allTransactions.filter {
                        it.type == TransactionType.BANK_TRANSFER
                    }.sumByDouble { it.amount }.toString()

                    binding.tvAirtimePurchaseAmount.text = "N " + allTransactions.filter {
                        it.type == TransactionType.AIRTIME_PURCHASE
                    }.sumByDouble { it.amount }.toString()
                }
            }
            is DashboardState.Error -> showSnackbar(dashboardState.error?.message.toString())
        }
    }

}
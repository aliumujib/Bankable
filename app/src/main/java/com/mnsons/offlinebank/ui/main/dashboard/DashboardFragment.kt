package com.mnsons.offlinebank.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mnsons.offlinebank.databinding.FragmentDashboardBinding
import com.mnsons.offlinebank.ui.commons.adapters.transaction.SectionedTransactionsAdapter
import com.mnsons.offlinebank.ui.commons.adapters.transaction.TransactionsAdapter
import com.mnsons.offlinebank.utils.TransactionUtil

class DashboardFragment : Fragment() {

//    @Inject
//    lateinit var dashboardViewModel: DashboardViewModel

    private val transactionsAdapter by lazy { TransactionsAdapter() }

    private val sectionedTransactionsAdapter by lazy {
        SectionedTransactionsAdapter(transactionsAdapter)
    }

    private lateinit var _binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.rvSectionedTransactions.adapter = sectionedTransactionsAdapter

        sectionedTransactionsAdapter.setTransactions(
            TransactionUtil.generateDummySectionedTransactions(
                TransactionUtil.dummyTransactions
            )
        )

    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        injectDependencies()
//    }
//
//    private fun injectDependencies() {
//        (requireActivity() as MainActivity).mainComponent.inject(this)
//    }

}
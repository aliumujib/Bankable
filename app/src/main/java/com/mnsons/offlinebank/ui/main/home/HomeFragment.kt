package com.mnsons.offlinebank.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.mnsons.offlinebank.contracts.CheckGTBankBalanceContract
import com.mnsons.offlinebank.databinding.FragmentHomeBinding
import com.mnsons.offlinebank.ui.commons.dialogs.SelectBankBottomSheet
import com.mnsons.offlinebank.ui.main.home.menu.MenuAction
import com.mnsons.offlinebank.ui.main.home.menu.MenuActionClickListener
import com.mnsons.offlinebank.ui.main.home.menu.MenuAdapter
import com.mnsons.offlinebank.utils.DummyData
import com.mnsons.offlinebank.utils.ext.dpToPx
import io.cabriole.decorator.GridSpanMarginDecoration

class HomeFragment : Fragment(), MenuActionClickListener {

    private lateinit var homeViewModel: HomeViewModel

    private val gtBankBalanceCall =
        registerForActivityResult(CheckGTBankBalanceContract()) { result ->
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            Log.i("MyActivity", "Obtained result: $result")
        }


    private lateinit var _binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val modelList = listOf(
            MenuAction.TransferFunds,
            MenuAction.BuyAirtime,
            MenuAction.BuyData,
            MenuAction.PayBills,
            MenuAction.CheckAccountBalance
        )

        val menuAdapter = MenuAdapter(this)
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 1 || position == 2) {
                    1
                } else {
                    2
                }
            }
        }

        _binding.recyclerView.apply {
            addItemDecoration(
                GridSpanMarginDecoration(
                    margin = requireContext().dpToPx(16),
                    gridLayoutManager = gridLayoutManager
                )
            )
            layoutManager = gridLayoutManager
            adapter = menuAdapter
        }

        menuAdapter.submitList(modelList)
    }

    override fun onMenuActionClick(model: MenuAction) {
        SelectBankBottomSheet(DummyData.banks) { bank ->
            when (model) {
                MenuAction.BuyAirtime -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationBuyAirtime(
                            bank
                        )
                    )
                }
                MenuAction.TransferFunds -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationTransferMoney(
                            bank
                        )
                    )
                }
                MenuAction.CheckAccountBalance -> {
                    gtBankBalanceCall.launch("19142a42")
                }
            }
        }.show(childFragmentManager, javaClass.simpleName)
    }

}
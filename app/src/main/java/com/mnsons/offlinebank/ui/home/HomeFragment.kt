package com.mnsons.offlinebank.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.artic.views.ext.dpToPx
import com.mnsons.offlinebank.contracts.CheckGTBankBalanceContract
import com.mnsons.offlinebank.databinding.FragmentHomeBinding
import com.mnsons.offlinebank.ui.home.menu.MenuAction
import com.mnsons.offlinebank.ui.home.menu.MenuActionClickListener
import com.mnsons.offlinebank.ui.home.menu.MenuAdapter
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

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val modelList = listOf<MenuAction>(
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
        if (model.id == 5) {
            gtBankBalanceCall.launch("19142a42")
        }
    }

}
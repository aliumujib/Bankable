package com.mnsons.offlinebank.ui.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.CheckBankBalanceContract
import com.mnsons.offlinebank.databinding.FragmentHomeBinding
import com.mnsons.offlinebank.di.main.home.DaggerHomeComponent
import com.mnsons.offlinebank.di.main.home.HomeModule
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.ui.commons.dialogs.SelectBottomSheet
import com.mnsons.offlinebank.ui.main.MainActivity.Companion.mainComponent
import com.mnsons.offlinebank.ui.main.home.menu.MenuAction
import com.mnsons.offlinebank.ui.main.home.menu.MenuActionClickListener
import com.mnsons.offlinebank.ui.main.home.menu.MenuAdapter
import com.mnsons.offlinebank.ui.main.presentation.MainState
import com.mnsons.offlinebank.ui.main.presentation.MainViewModel
import com.mnsons.offlinebank.utils.ext.dpToPx
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import io.cabriole.decorator.GridSpanMarginDecoration
import javax.inject.Inject

class HomeFragment : Fragment(), MenuActionClickListener {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var homeViewModel: HomeViewModel

    private val gtBankBalanceCall =
        registerForActivityResult(CheckBankBalanceContract()) { result ->
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            Log.i("MyActivity", "Obtained result: $result")
        }

    private lateinit var _binding: FragmentHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    private fun injectDependencies() {
        DaggerHomeComponent.builder()
            .mainComponent(mainComponent(requireActivity()))
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        nonNullObserve(mainViewModel.state, ::handleStates)

    }


    private fun handleStates(mainState: MainState) {
        if (mainState is MainState.Idle) {
            mainState.user?.let {
                _binding.nameIntro.text =
                    requireContext().getString(R.string.home_hello_intro, it.firstName)
            }
        }
    }

    override fun onMenuActionClick(model: MenuAction) {
        mainViewModel.state.value?.user?.banks?.let {
            SelectBottomSheet(it) { bank ->
                navigate(model, bank)
            }.show(childFragmentManager, javaClass.simpleName)
        }
    }

    private fun navigate(model: MenuAction, bank: BankModel) {
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
                //gtBankMenuCall.launch(Unit)
            }
        }
    }

}
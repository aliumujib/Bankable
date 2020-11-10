package com.mnsons.offlinebank.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.CheckBankBalanceContract
import com.mnsons.offlinebank.databinding.FragmentHomeBinding
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.ui.commons.dialogs.SelectBottomSheet
import com.mnsons.offlinebank.ui.main.accountbalance.AccountBalanceFragment
import com.mnsons.offlinebank.ui.main.home.menu.MenuAction
import com.mnsons.offlinebank.ui.main.home.menu.MenuActionClickListener
import com.mnsons.offlinebank.ui.main.home.menu.MenuAdapter
import com.mnsons.offlinebank.ui.main.presentation.MainState
import com.mnsons.offlinebank.ui.main.presentation.MainViewModel
import com.mnsons.offlinebank.utils.CheckBalanceUtil
import com.mnsons.offlinebank.utils.ext.dpToPx
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.showSnackbar
import com.mnsons.offlinebank.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.cabriole.decorator.GridSpanMarginDecoration

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), MenuActionClickListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private val accountBalanceCall =
        registerForActivityResult(CheckBankBalanceContract()) { result ->
            if (result.data != null) {
                val stringList =
                    result.data?.split(')')
                        ?.filter { it.contains("NGN") }
                        ?.map {
                            it.replace(":", "")
                            it.replace("(", "")
                        }

                stringList?.let {
                    AccountBalanceFragment.display(
                        childFragmentManager,
                        it
                    ) {
                        findNavController().popBackStack()
                    }
                }
            } else {
                result.message?.let {
                    showSnackbar(it)
                }
            }
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

        binding.recyclerView.apply {
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
                binding.nameIntro.text = requireContext().getString(R.string.home_hello_intro, it.firstName)
            }
        }
    }

    override fun onMenuActionClick(model: MenuAction) {
        if (model is MenuAction.PayBills || model is MenuAction.BuyData) {
            showSnackbar("This operation is not yet available!")
            return
        }

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
                CheckBalanceUtil.getActionIdByBankId(bank.id)?.let {
                    accountBalanceCall.launch(it)
                } ?: showSnackbar("Account Balance Check for ${getString(bank.bankName)} is currently not supported")
            }
            MenuAction.BuyData -> {

            }
            MenuAction.PayBills -> {

            }
        }
    }

}
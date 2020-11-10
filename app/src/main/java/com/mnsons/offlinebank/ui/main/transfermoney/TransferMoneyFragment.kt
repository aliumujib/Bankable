package com.mnsons.offlinebank.ui.main.transfermoney

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.MoneyTransferContract
import com.mnsons.offlinebank.databinding.FragmentTransferMoneyBinding
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.ui.commons.dialogs.SelectFromMenuBottomSheet
import com.mnsons.offlinebank.ui.commons.dialogs.TransactionStatusDialog
import com.mnsons.offlinebank.utils.TransferMoneyUtil
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.onBackPressed
import com.mnsons.offlinebank.utils.ext.showSnackbar
import com.mnsons.offlinebank.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferMoneyFragment : Fragment(R.layout.fragment_transfer_money) {

    private val binding: FragmentTransferMoneyBinding by viewBinding(FragmentTransferMoneyBinding::bind)

   private val transferMoneyViewModel: TransferMoneyViewModel by viewModels()

    private val sourceBank: TransferMoneyFragmentArgs by navArgs()

    private val moneyTransferContract =
        registerForActivityResult(MoneyTransferContract()) { result ->

            if (result.data != null) {
                transferMoneyViewModel.persistTransaction(TransactionStatus.SUCCESS)
            } else {
                transferMoneyViewModel.persistTransaction(TransactionStatus.FAILED)
            }

            TransactionStatusDialog.display(
                childFragmentManager,
                result.data != null,
                result.message ?: requireContext().getString(R.string.success)
            ) {
                findNavController().popBackStack()
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transferMoneyViewModel.fetchBankMenu(sourceBank.bank.id)

        binding.root.setInAnimation(context, R.anim.nav_default_enter_anim)
        binding.root.setOutAnimation(context, R.anim.nav_default_exit_anim)

        binding.transferDetailsContainer.btnNext.setOnClickListener {
            transferMoneyViewModel.initiateFundTransfer(
                TransferMoneyUtil.getActionIdByBankId(sourceBank.bank.id),
                binding.transferDetailsContainer.etAmount.text.toString(),
                binding.transferDetailsContainer.accountNumber.text.toString(),
                requireContext().getString(sourceBank.bank.bankName)
            )
        }

        binding.transferDetailsContainer.recipientBank.setOnClickListener {
            openBankSelector()
        }

        nonNullObserve(transferMoneyViewModel.state, ::handleStates)
    }

    private fun handleStates(transferMoneyState: TransferMoneyState) {
        when (transferMoneyState) {
            is TransferMoneyState.Initialize -> {
                transferMoneyState.transferModel?.let {
                    moneyTransferContract.launch(it)
                }
            }
            is TransferMoneyState.Error -> showSnackbar(transferMoneyState.error?.message.toString())
        }
    }


    private fun openBankSelector() {
        SelectFromMenuBottomSheet(transferMoneyViewModel.bankIds) { bank ->
            binding.transferDetailsContainer.recipientBank.setText(bank.bankName)
            transferMoneyViewModel.setRecipientBank(bank.id)
        }.show(childFragmentManager, javaClass.simpleName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressed {
            if (binding.root.displayedChild == VIEW_ENTER_PIN
                || binding.root.displayedChild == VIEW_ENTER_PIN
            ) {
                binding.root.displayedChild = VIEW_ENTER_DETAILS
                return@onBackPressed
            }
        }

    }

    companion object {
        const val VIEW_ENTER_DETAILS = 0
        const val VIEW_ENTER_PIN = 2
    }

}
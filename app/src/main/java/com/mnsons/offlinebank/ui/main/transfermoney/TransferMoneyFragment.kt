package com.mnsons.offlinebank.ui.main.transfermoney

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.MoneyTransferContract
import com.mnsons.offlinebank.databinding.FragmentTransferMoneyBinding
import com.mnsons.offlinebank.di.main.transferfunds.DaggerTransferFundsComponent
import com.mnsons.offlinebank.di.main.transferfunds.TransferFundsModule
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.ui.commons.dialogs.SelectFromMenuBottomSheet
import com.mnsons.offlinebank.ui.commons.dialogs.TransactionStatusDialog
import com.mnsons.offlinebank.ui.main.MainActivity.Companion.mainComponent
import com.mnsons.offlinebank.utils.TransferMoneyUtil
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.onBackPressed
import javax.inject.Inject

class TransferMoneyFragment : Fragment() {

    private lateinit var _binding: FragmentTransferMoneyBinding

    @Inject
    lateinit var transferMoneyViewModel: TransferMoneyViewModel

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

    private fun injectDependencies() {
        DaggerTransferFundsComponent.builder()
            .mainComponent(mainComponent(requireActivity()))
            .transferFundsModule(TransferFundsModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferMoneyBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transferMoneyViewModel.fetchBankMenu(sourceBank.bank.id)

        _binding.root.setInAnimation(context, R.anim.nav_default_enter_anim)
        _binding.root.setOutAnimation(context, R.anim.nav_default_exit_anim)

        _binding.transferDetailsContainer.btnNext.setOnClickListener {
            transferMoneyViewModel.initiateFundTransfer(
                TransferMoneyUtil.getActionIdByBankId(sourceBank.bank.id),
                _binding.transferDetailsContainer.etAmount.text.toString(),
                _binding.transferDetailsContainer.accountNumber.text.toString(),
                requireContext().getString(sourceBank.bank.bankName)
            )
        }

        _binding.transferDetailsContainer.recipientBank.setOnClickListener {
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
            is TransferMoneyState.Error -> {
                Snackbar.make(
                    _binding.root,
                    transferMoneyState.error?.message.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun openBankSelector() {
        SelectFromMenuBottomSheet(transferMoneyViewModel.bankIds) { bank ->
            _binding.transferDetailsContainer.recipientBank.setText(bank.bankName)
            transferMoneyViewModel.setRecipientBank(bank.id)
        }.show(childFragmentManager, javaClass.simpleName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
        onBackPressed {
            if (_binding.root.displayedChild == VIEW_ENTER_PIN
                || _binding.root.displayedChild == VIEW_ENTER_PIN
            ) {
                _binding.root.displayedChild = VIEW_ENTER_DETAILS
                return@onBackPressed
            }
        }

    }

    companion object {
        const val VIEW_ENTER_DETAILS = 0
        const val VIEW_SELECT_BANK = 1
        const val VIEW_ENTER_PIN = 2
        const val VIEW_SAVE_BENEFICIARY = 3
    }

}
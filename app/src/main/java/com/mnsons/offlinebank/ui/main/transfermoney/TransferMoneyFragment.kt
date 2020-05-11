package com.mnsons.offlinebank.ui.main.transfermoney

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.gtb.MoneyTransferContract
import com.mnsons.offlinebank.databinding.FragmentTransferMoneyBinding
import com.mnsons.offlinebank.ui.commons.dialogs.SelectFromMenuBottomSheet
import com.mnsons.offlinebank.utils.ext.onBackPressed
import javax.inject.Inject

class TransferMoneyFragment : Fragment() {

    private lateinit var _binding: FragmentTransferMoneyBinding

    @Inject
    lateinit var transferMoneyViewModel: TransferMoneyViewModel

    private val gtBankMoneyTransferMenu =
        registerForActivityResult(MoneyTransferContract()) { result ->
            Log.d("TTresult", result)
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

        _binding.root.setInAnimation(context, R.anim.nav_default_enter_anim)
        _binding.root.setOutAnimation(context, R.anim.nav_default_exit_anim)

        _binding.transferDetailsContainer.btnNext.setOnClickListener {
            if (validateInput()) {
                gtBankMoneyTransferMenu.launch(Unit)
            } else {
                "show error"
            }
        }

        _binding.transferDetailsContainer.recipientBank.setOnClickListener {
            openBankSelector()
        }

        _binding.selectBankContainer.btnNext.setOnClickListener {
            _binding.root.displayedChild = VIEW_ENTER_PIN
        }

        _binding.selectBankContainer.btnCancel.setOnClickListener {
            _binding.root.displayedChild = VIEW_ENTER_DETAILS
        }

        _binding.transferPinContainer.btnNext.setOnClickListener {
            _binding.root.displayedChild = VIEW_SAVE_BENEFICIARY
        }

        _binding.transferPinContainer.btnCancel.setOnClickListener {
            _binding.root.displayedChild = VIEW_ENTER_DETAILS
        }

        _binding.saveTransferBeneficiaryContainer.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        _binding.saveTransferBeneficiaryContainer.btnYes.setOnClickListener {

        }

        _binding.saveTransferBeneficiaryContainer.btnNo.setOnClickListener {

        }
    }

    private fun validateInput(): Boolean {
        return when {
            _binding.transferDetailsContainer.accountNumber.text?.isEmpty() == true -> {
                false
            }
            _binding.transferDetailsContainer.recipientBank.text?.isEmpty() == true -> {
                false
            }
            _binding.transferDetailsContainer.etAmount.text?.isEmpty() == true -> {
                false
            }
            else -> {
                true
            }
        }
    }

    private fun openBankSelector() {
        SelectFromMenuBottomSheet(transferMoneyViewModel.bankIds) { bank ->

        }.show(childFragmentManager, javaClass.simpleName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

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
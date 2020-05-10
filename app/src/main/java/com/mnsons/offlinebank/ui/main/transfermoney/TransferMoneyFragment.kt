package com.mnsons.offlinebank.ui.main.transfermoney

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentTransferMoneyBinding
import com.mnsons.offlinebank.utils.ext.onBackPressed

class TransferMoneyFragment : Fragment() {

    private lateinit var _binding: FragmentTransferMoneyBinding

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
            _binding.root.displayedChild = VIEW_SELECT_BANK
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onBackPressed {
            if (_binding.root.displayedChild == VIEW_ENTER_PIN
                || _binding.root.displayedChild == VIEW_ENTER_PIN) {
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
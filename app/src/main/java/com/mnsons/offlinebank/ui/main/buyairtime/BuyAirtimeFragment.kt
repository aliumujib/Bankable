package com.mnsons.offlinebank.ui.main.buyairtime

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentBuyAirtimeBinding
import com.mnsons.offlinebank.utils.ext.onBackPressed

class BuyAirtimeFragment : Fragment() {

    private lateinit var _binding: FragmentBuyAirtimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyAirtimeBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.root.setInAnimation(context, R.anim.nav_default_enter_anim)
        _binding.root.setOutAnimation(context, R.anim.nav_default_exit_anim)

        _binding.airtimeDetailsContainer.btnNext.setOnClickListener {
            _binding.root.displayedChild = VIEW_ENTER_PIN
        }

        _binding.airtimePinContainer.btnNext.setOnClickListener {
            _binding.root.displayedChild = VIEW_SAVE_BENEFICIARY
        }

        _binding.airtimePinContainer.btnCancel.setOnClickListener {
            _binding.root.displayedChild = VIEW_ENTER_DETAILS
        }

        _binding.saveAirtimeBeneficiaryContainer.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        _binding.saveAirtimeBeneficiaryContainer.btnYes.setOnClickListener {

        }

        _binding.saveAirtimeBeneficiaryContainer.btnNo.setOnClickListener {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onBackPressed {
            if (_binding.root.displayedChild == VIEW_ENTER_PIN) {
                _binding.root.displayedChild = VIEW_ENTER_DETAILS
                return@onBackPressed
            }
        }

    }

    companion object {
        const val VIEW_ENTER_DETAILS = 0
        const val VIEW_ENTER_PIN = 1
        const val VIEW_SAVE_BENEFICIARY = 2
    }

}
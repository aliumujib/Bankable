package com.mnsons.offlinebank.ui.main.profile.userdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentUserDetailsBinding
import com.mnsons.offlinebank.ui.commons.adapters.bank.BankSelectionAdapter
import com.mnsons.offlinebank.ui.main.presentation.MainState
import com.mnsons.offlinebank.ui.main.presentation.MainViewModel
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private val binding: FragmentUserDetailsBinding by viewBinding(FragmentUserDetailsBinding::bind)

   private val mainViewModel: MainViewModel by activityViewModels()

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nonNullObserve(mainViewModel.state, ::handleStates)

        binding.btnAddBank.setOnClickListener {
            findNavController().navigate(UserDetailsFragmentDirections.actionNavigationProfileToNavigationAddBank())
        }
    }


    private fun handleStates(mainState: MainState) {
        if (mainState is MainState.Idle) {
            mainState.user?.let {
                binding.tvUserFullName.text = "${it.firstName} ${it.lastName}"
                binding.tvUserPhoneNumber.text = it.phoneNumber
                bankSelectionAdapter.all = it.banks
                binding.rvSelectedBanks.adapter = bankSelectionAdapter
            }
        }
    }

}
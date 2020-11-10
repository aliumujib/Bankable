package com.mnsons.offlinebank.ui.onboarding.collectuserdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentCollectUserDetailsBinding
import com.mnsons.offlinebank.ui.onboarding.OnBoardingActivity
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingState
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingViewModel
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.viewBinding

class CollectUserDetailsFragment : Fragment(R.layout.fragment_collect_user_details) {

    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    private val binding: FragmentCollectUserDetailsBinding by viewBinding(FragmentCollectUserDetailsBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            onBoardingViewModel.setUserDetails(
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etPhoneNumber.text.toString()
            )
        }

        nonNullObserve(onBoardingViewModel.state, ::handleState)
    }


    private fun handleState(onBoardingState: OnBoardingState) {
        when (onBoardingState) {
            is OnBoardingState.Loading -> {

            }
            is OnBoardingState.Finished -> {

            }
            is OnBoardingState.Editing -> {
                findNavController().navigate(R.id.action_navigation_collect_user_details_to_navigation_select_user_banks)
            }
            is OnBoardingState.Error -> {
                Snackbar.make(
                    binding.root,
                    onBoardingState.error?.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

}
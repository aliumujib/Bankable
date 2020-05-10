package com.mnsons.offlinebank.ui.onboarding.collectuserdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.ApplicationClass.Companion.coreComponent
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentCollectUserDetailsBinding
import com.mnsons.offlinebank.di.onboarding.DaggerOnBoardingComponent
import com.mnsons.offlinebank.di.onboarding.OnBoardingModule
import com.mnsons.offlinebank.ui.onboarding.OnBoardingActivity
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingState
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingViewModel
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import javax.inject.Inject

class CollectUserDetailsFragment : Fragment() {

    @Inject
    lateinit var onBoardingViewModel: OnBoardingViewModel

    private lateinit var _binding: FragmentCollectUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectUserDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnNext.setOnClickListener {
            onBoardingViewModel.setUserDetails(
                _binding.etFirstName.text.toString(),
                _binding.etLastName.text.toString(),
                _binding.etPhoneNumber.text.toString()
            )
        }

        nonNullObserve(onBoardingViewModel.state, ::handleState)
    }

    private fun injectDependencies() {
        DaggerOnBoardingComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .onBoardingModule(OnBoardingModule(requireActivity() as OnBoardingActivity))
            .build()
            .inject(this)
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
                    _binding.root,
                    onBoardingState.error?.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

}
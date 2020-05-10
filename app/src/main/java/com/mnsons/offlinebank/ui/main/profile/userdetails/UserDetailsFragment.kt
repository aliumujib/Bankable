package com.mnsons.offlinebank.ui.main.profile.userdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.ApplicationClass
import com.mnsons.offlinebank.ApplicationClass.Companion.coreComponent
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentUserDetailsBinding
import com.mnsons.offlinebank.di.main.DaggerMainComponent
import com.mnsons.offlinebank.di.main.MainModule
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.ui.main.presentation.MainState
import com.mnsons.offlinebank.ui.main.presentation.MainViewModel
import com.mnsons.offlinebank.ui.onboarding.OnBoardingActivity
import com.mnsons.offlinebank.utils.DummyData
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import javax.inject.Inject

class UserDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentUserDetailsBinding

    @Inject
    lateinit var mainViewModel: MainViewModel

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nonNullObserve(mainViewModel.state, ::handleStates)

        _binding.btnAddBank.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_add_bank)
        }
    }


    private fun handleStates(mainState: MainState) {
        if (mainState is MainState.Idle) {
            mainState.user?.let {
                _binding.tvUserFullName.text = "${it.firstName} ${it.lastName}"
                _binding.tvUserPhoneNumber.text = it.phoneNumber
                Log.d(UserDetailsFragment::class.java.simpleName, it.banks.toString())
                bankSelectionAdapter.all = it.banks
                _binding.rvSelectedBanks.adapter = bankSelectionAdapter
            }
        }
    }

    private fun injectDependencies() {
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }


}
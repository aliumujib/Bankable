package com.mnsons.offlinebank.ui.onboarding.selectuserbanks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.ApplicationClass
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.databinding.FragmentSelectUserBanksBinding
import com.mnsons.offlinebank.di.onboarding.DaggerOnBoardingComponent
import com.mnsons.offlinebank.di.onboarding.OnBoardingModule
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionListener
import com.mnsons.offlinebank.ui.commons.banks.BanksPopulator
import com.mnsons.offlinebank.ui.onboarding.OnBoardingActivity
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingState
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingViewModel
import com.mnsons.offlinebank.utils.ext.observe
import kotlinx.android.synthetic.main.fragment_select_user_banks.*
import javax.inject.Inject

class SelectUserBanksFragment : Fragment(),
    BankSelectionListener<BankModel> {

    private lateinit var _binding: FragmentSelectUserBanksBinding

    @Inject
    lateinit var onBoardingViewModel: OnBoardingViewModel

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter(
            BankSelectionAdapter.ViewType.SELECTABLE,
            this
        ).apply {
            all = BanksPopulator.fetchSupportedBanks()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    private fun injectDependencies() {
        DaggerOnBoardingComponent
            .builder()
            .coreComponent(ApplicationClass.coreComponent(requireContext()))
            .onBoardingModule(OnBoardingModule(requireActivity() as OnBoardingActivity))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectUserBanksBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding.rvBanks.adapter = bankSelectionAdapter

        _binding.searchContainer.setOnClickListener {
            _binding.searchHint.visibility = View.GONE
            _binding.searchBar.isIconified = !search_bar.isIconified
        }

        _binding.searchBar.setOnCloseListener {
            _binding.searchHint.visibility = View.VISIBLE
            return@setOnCloseListener false
        }

        _binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    bankSelectionAdapter.filter(it)
                }
                return false
            }
        })

        _binding.btnNext.setOnClickListener {
            onBoardingViewModel.setUserBanks(bankSelectionAdapter.selected)
        }

        observe(onBoardingViewModel.state, ::handleState)
    }

    private fun handleState(onBoardingState: OnBoardingState) {

        when (onBoardingState) {
            is OnBoardingState.Loading -> {

            }
            is OnBoardingState.Finished -> {
                onBoardingViewModel.finishSetup()
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }
            is OnBoardingState.Editing -> {
                _binding.tvFirstName.text = "${onBoardingState.user?.firstName},"
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

    override fun select(item: BankModel) {
        bankSelectionAdapter.addToSelected(item)
    }

    override fun deselect(item: BankModel) {
        bankSelectionAdapter.removeFromSelected(item)
    }

}
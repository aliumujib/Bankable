package com.mnsons.offlinebank.ui.onboarding.selectuserbanks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentSelectUserBanksBinding
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.ui.commons.adapters.SelectionListener
import com.mnsons.offlinebank.ui.commons.adapters.bank.BankSelectionAdapter
import com.mnsons.offlinebank.ui.commons.banks.BanksPopulator
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingState
import com.mnsons.offlinebank.ui.onboarding.presentation.OnBoardingViewModel
import com.mnsons.offlinebank.utils.ext.observe
import com.mnsons.offlinebank.utils.ext.viewBinding
import kotlinx.android.synthetic.main.fragment_select_user_banks.*

class SelectUserBanksFragment : Fragment(R.layout.fragment_select_user_banks), SelectionListener<BankModel> {

    private val binding: FragmentSelectUserBanksBinding by viewBinding(FragmentSelectUserBanksBinding::bind)

    private val onBoardingViewModel: OnBoardingViewModel by viewModels()


    private var bankTransferMenuIndexer: BankTransferMenuIndexer = BankTransferMenuIndexer(
        { id, results ->
            onBoardingViewModel.saveMenuForBank(id, results)
        }, {
            onBoardingViewModel.finishSetup()
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        },
        this
    )

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter(
            BankSelectionAdapter.ViewType.SELECTABLE,
            this
        ).apply {
            all = BanksPopulator.fetchSupportedBanks()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvBanks.adapter = bankSelectionAdapter

        binding.searchContainer.setOnClickListener {
            binding.searchHint.visibility = View.GONE
            binding.searchBar.isIconified = !search_bar.isIconified
        }

        binding.searchBar.setOnCloseListener {
            binding.searchHint.visibility = View.VISIBLE
            return@setOnCloseListener false
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        binding.btnNext.setOnClickListener {
            onBoardingViewModel.setUserBanks(bankSelectionAdapter.selected)
        }

        observe(onBoardingViewModel.state, ::handleState)
    }

    private fun handleState(onBoardingState: OnBoardingState) {

        when (onBoardingState) {
            is OnBoardingState.Loading -> {

            }
            is OnBoardingState.Finished -> {
                bankTransferMenuIndexer.indexMenuForBanks(bankSelectionAdapter.selected)
            }
            is OnBoardingState.Editing -> {
                binding.tvFirstName.text = "${onBoardingState.user?.firstName},"
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

    override fun select(item: BankModel) {
        bankSelectionAdapter.addToSelected(item)
    }

    override fun deselect(item: BankModel) {
        bankSelectionAdapter.removeFromSelected(item)
    }

}
package com.mnsons.offlinebank.ui.main.profile.addbank

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentAddBankBinding
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.ui.commons.adapters.SelectionListener
import com.mnsons.offlinebank.ui.commons.adapters.bank.BankSelectionAdapter
import com.mnsons.offlinebank.ui.commons.banks.BanksPopulator
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.showSnackbar
import com.mnsons.offlinebank.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_bank.*

@AndroidEntryPoint
class AddBankFragment : Fragment(R.layout.fragment_add_bank),
    SelectionListener<BankModel> {

    private val binding: FragmentAddBankBinding by viewBinding(FragmentAddBankBinding::bind)

    private val addBankViewModel: AddBankViewModel by viewModels()

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
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                bankSelectionAdapter.filter(newText)
                return false
            }
        })

        binding.btnAddToMyBanks.setOnClickListener {
            addBankViewModel.updateUserBanks(bankSelectionAdapter.selected)
        }

        nonNullObserve(addBankViewModel.state, ::handleStates)
    }

    private fun handleStates(addBankState: AddBankState) {
        when (addBankState) {
            is AddBankState.Idle -> {
                addBankState.user?.let {
                    bankSelectionAdapter.selected.addAll(it.banks)
                    binding.rvBanks.adapter = bankSelectionAdapter
                }
            }
            is AddBankState.Editing -> {
                findNavController().navigateUp()
            }
            is AddBankState.Error -> showSnackbar(addBankState.error?.message.toString())
        }
    }

    override fun select(item: BankModel) {
        bankSelectionAdapter.addToSelected(item)
    }

    override fun deselect(item: BankModel) {
        bankSelectionAdapter.removeFromSelected(item)
    }


}
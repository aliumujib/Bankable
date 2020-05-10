package com.mnsons.offlinebank.ui.main.profile.addbank

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.databinding.FragmentAddBankBinding
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionListener
import com.mnsons.offlinebank.ui.commons.banks.BanksPopulator
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.ui.main.presentation.MainState
import com.mnsons.offlinebank.ui.main.presentation.MainViewModel
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import kotlinx.android.synthetic.main.fragment_add_bank.*
import javax.inject.Inject

class AddBankFragment : Fragment(),
    BankSelectionListener<BankModel> {

    private lateinit var _binding: FragmentAddBankBinding

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var addBankViewModel: AddBankViewModel

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBankBinding.inflate(inflater, container, false)
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
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                bankSelectionAdapter.filter(newText)
                return false
            }
        })

        _binding.btnAddToMyBanks.setOnClickListener {
            addBankViewModel.updateUserBanks(bankSelectionAdapter.selected)
        }

        nonNullObserve(addBankViewModel.state, ::handleStates)
    }

    private fun handleStates(addBankState: AddBankState) {
        when (addBankState) {
            is AddBankState.Idle -> {
                addBankState.user?.let {
                    bankSelectionAdapter.selected.addAll(it.banks)
                    _binding.rvBanks.adapter = bankSelectionAdapter
                }
            }
            is AddBankState.Editing -> {
                findNavController().navigateUp()
            }
            is AddBankState.Error -> {
                Snackbar.make(
                    _binding.root,
                    addBankState.error?.message.toString(),
                    Snackbar.LENGTH_SHORT
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

    private fun injectDependencies() {
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

}
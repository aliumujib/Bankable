package com.mnsons.offlinebank.ui.main.profile.addbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.databinding.FragmentAddBankBinding
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.utils.DummyData
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionListener
import kotlinx.android.synthetic.main.fragment_add_bank.*

class AddBankFragment : Fragment(),
    BankSelectionListener<BankModel> {

    private lateinit var _binding: FragmentAddBankBinding

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter(
            BankSelectionAdapter.ViewType.SELECTABLE,
            this
        ).apply {
            all = DummyData.banks
            selected = DummyData.banks.subList(0, 1)
        }
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
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        _binding.btnAddToMyBanks.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun select(item: BankModel) {
        bankSelectionAdapter.addToSelected(item)
    }

    override fun deselect(item: BankModel) {
        bankSelectionAdapter.removeFromSelected(item)
    }

}
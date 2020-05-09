package com.mnsons.offlinebank.ui.profile.addbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.ui.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.utils.DummyData
import com.mnsons.offlinebank.utils.MultiSelectListener
import kotlinx.android.synthetic.main.fragment_add_bank.*

class AddBankFragment : Fragment(), MultiSelectListener<BankModel> {

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
        return inflater.inflate(R.layout.fragment_add_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBanks.adapter = bankSelectionAdapter

        rvBanks.adapter = bankSelectionAdapter

        searchContainer.setOnClickListener {
            search_hint.visibility = View.GONE
            search_bar.isIconified = !search_bar.isIconified
        }

        search_bar.setOnCloseListener {
            search_hint.visibility = View.VISIBLE
            return@setOnCloseListener false
        }

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        btnAddToMyBanks.setOnClickListener {
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
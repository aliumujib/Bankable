package com.mnsons.offlinebank.ui.selectuserbanks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.ui.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.utils.MultiSelectListener
import kotlinx.android.synthetic.main.fragment_select_user_banks.*

class SelectUserBanksFragment : Fragment(), MultiSelectListener<BankModel> {

    private val bankSelectionAdapter by lazy { BankSelectionAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_user_banks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvFirstName.text = "Quadri,"

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

        bankSelectionAdapter.all = arrayListOf(
            BankModel("Guaranty Trust Bank"),
            BankModel("Access Bank"),
            BankModel("Zenith Bank")
        )

        rvBanks.adapter = bankSelectionAdapter
    }

    override fun select(item: BankModel) {
        bankSelectionAdapter.addToSelected(item)
    }

    override fun deselect(item: BankModel) {
        bankSelectionAdapter.removeFromSelected(item)
    }

}
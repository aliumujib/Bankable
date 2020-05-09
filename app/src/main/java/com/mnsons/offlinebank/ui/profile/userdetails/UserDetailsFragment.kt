package com.mnsons.offlinebank.ui.profile.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.ui.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.utils.DummyData
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment : Fragment() {

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter().apply {
            all = DummyData.banks
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUserFullName.text = "Quadri Anifowose"
        tvUserPhoneNumber.text = "08177175473"

        rvSelectedBanks.adapter = bankSelectionAdapter

        btnAddBank.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_add_bank)
        }
    }

}
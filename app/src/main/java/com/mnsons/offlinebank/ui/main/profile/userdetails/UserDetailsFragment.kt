package com.mnsons.offlinebank.ui.main.profile.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentUserDetailsBinding
import com.mnsons.offlinebank.ui.commons.adapters.BankSelectionAdapter
import com.mnsons.offlinebank.utils.DummyData

class UserDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentUserDetailsBinding

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter().apply {
            all = DummyData.banks
        }
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

        _binding.tvUserFullName.text = "Quadri Anifowose"
        _binding.tvUserPhoneNumber.text = "08177175473"

        _binding.rvSelectedBanks.adapter = bankSelectionAdapter

        _binding.btnAddBank.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_add_bank)
        }
    }

}
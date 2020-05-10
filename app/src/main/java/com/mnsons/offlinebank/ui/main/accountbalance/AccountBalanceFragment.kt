package com.mnsons.offlinebank.ui.main.accountbalance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mnsons.offlinebank.databinding.FragmentAccountBalanceBinding

class AccountBalanceFragment : Fragment() {

    private lateinit var _binding: FragmentAccountBalanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBalanceBinding.inflate(inflater, container, false)

        return _binding.root
    }

}
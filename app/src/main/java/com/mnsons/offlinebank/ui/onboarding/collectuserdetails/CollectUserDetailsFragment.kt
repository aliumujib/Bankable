package com.mnsons.offlinebank.ui.onboarding.collectuserdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentCollectUserDetailsBinding

class CollectUserDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentCollectUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentCollectUserDetailsBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnNext.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_collect_user_details_to_navigation_select_user_banks
            )
        }
    }

}
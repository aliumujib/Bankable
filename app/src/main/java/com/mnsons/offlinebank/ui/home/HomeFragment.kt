package com.mnsons.offlinebank.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.CheckGTBankBalanceContract

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val gtBankBalanceCall =
        registerForActivityResult(CheckGTBankBalanceContract()) { result ->
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            Log.i("MyActivity", "Obtained result: $result")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: Button = root.findViewById(R.id.text_home)

        textView.setOnClickListener {
            gtBankBalanceCall.launch("19142a42")
        }

        return root
    }


}
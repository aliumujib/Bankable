package com.mnsons.offlinebank.ui.main.accountbalance

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.contracts.CheckBankBalanceContract
import com.mnsons.offlinebank.databinding.FragmentAccountBalanceBinding
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.utils.CheckBalanceUtil
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import javax.inject.Inject

class AccountBalanceFragment : Fragment() {

    private lateinit var _binding: FragmentAccountBalanceBinding

    @Inject
    lateinit var accountBalanceViewModel: AccountBalanceViewModel

    private val accountBalanceCall =
        registerForActivityResult(CheckBankBalanceContract()) { result ->
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            Log.i(javaClass.simpleName, "Obtained result: $result")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBalanceBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountBalanceViewModel.bank = AccountBalanceFragmentArgs.fromBundle(requireArguments()).bank
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountBalanceViewModel.fetchAccountBalance()

        _binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        nonNullObserve(accountBalanceViewModel.state, ::handleStates)
    }

    private fun handleStates(accountBalanceState: AccountBalanceState) {
        when (accountBalanceState) {
            is AccountBalanceState.Fetching -> {
                accountBalanceCall.launch(
                    CheckBalanceUtil.getActionIdByBankId(accountBalanceViewModel.bank.id)
                )
            }
            is AccountBalanceState.Fetched -> {

            }
            is AccountBalanceState.Error -> {
                Snackbar.make(
                    _binding.root,
                    accountBalanceState.error?.message.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    private fun injectDependencies() {
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

}
package com.mnsons.offlinebank.ui.main.buyairtime

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.BuyAirtimeContract
import com.mnsons.offlinebank.databinding.FragmentBuyAirtimeBinding
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.utils.BuyAirtimeUtil
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import javax.inject.Inject

class BuyAirtimeFragment : Fragment() {

    private lateinit var _binding: FragmentBuyAirtimeBinding

    @Inject
    lateinit var buyAirtimeViewModel: BuyAirtimeViewModel

    private val buyAirtimeCall =
        registerForActivityResult(BuyAirtimeContract()) { result ->
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            Log.i(javaClass.simpleName, "Obtained result: $result")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyAirtimeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buyAirtimeViewModel.bank = BuyAirtimeFragmentArgs.fromBundle(requireArguments()).bank

        _binding.root.setInAnimation(context, R.anim.nav_default_enter_anim)
        _binding.root.setOutAnimation(context, R.anim.nav_default_exit_anim)

        _binding.airtimeDetailsContainer.btnNext.setOnClickListener {
            buyAirtimeViewModel.initiateBuyAirtime(
                BuyAirtimeUtil.getActionIdByBankId(buyAirtimeViewModel.bank.id),
                _binding.airtimeDetailsContainer.etAmount.text.toString(),
                _binding.airtimeDetailsContainer.etPhoneNumber.text.toString()
            )
        }

        nonNullObserve(buyAirtimeViewModel.state, ::handleStates)
    }

    private fun handleStates(buyAirtimeState: BuyAirtimeState) {
        when (buyAirtimeState) {
            is BuyAirtimeState.Initialize -> {
                buyAirtimeState.buyAirtimeModel?.let {
                    buyAirtimeCall.launch(it)
                }
            }
            is BuyAirtimeState.Error -> {
                Snackbar.make(
                    _binding.root,
                    buyAirtimeState.error?.message.toString(),
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
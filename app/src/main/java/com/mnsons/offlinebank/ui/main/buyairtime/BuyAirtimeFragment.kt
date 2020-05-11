package com.mnsons.offlinebank.ui.main.buyairtime

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.contracts.BuyAirtimeContract
import com.mnsons.offlinebank.databinding.FragmentBuyAirtimeBinding
import com.mnsons.offlinebank.model.transaction.TransactionStatus
import com.mnsons.offlinebank.ui.commons.dialogs.TransactionStatusDialog
import com.mnsons.offlinebank.ui.main.MainActivity
import com.mnsons.offlinebank.utils.BuyAirtimeUtil
import com.mnsons.offlinebank.utils.ext.nonNullObserve
import com.mnsons.offlinebank.utils.ext.showSnackbar
import javax.inject.Inject

class BuyAirtimeFragment : Fragment() {

    private lateinit var _binding: FragmentBuyAirtimeBinding

    @Inject
    lateinit var buyAirtimeViewModel: BuyAirtimeViewModel

    private val bankArg: BuyAirtimeFragmentArgs by navArgs()

    private val buyAirtimeCall =
        registerForActivityResult(BuyAirtimeContract()) { result ->
            if (result.data != null) {
                buyAirtimeViewModel.persistTransaction(TransactionStatus.SUCCESS)
            } else {
                buyAirtimeViewModel.persistTransaction(TransactionStatus.FAILED)
            }

            TransactionStatusDialog.display(
                childFragmentManager,
                result.data != null,
                result.message ?: requireContext().getString(R.string.success)
            ) {
                findNavController().popBackStack()
            }
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
                _binding.airtimeDetailsContainer.etPhoneNumber.text.toString(),
                requireContext().getString(bankArg.bank.bankName)
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
            is BuyAirtimeState.Error -> showSnackbar(buyAirtimeState.error?.message.toString())
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
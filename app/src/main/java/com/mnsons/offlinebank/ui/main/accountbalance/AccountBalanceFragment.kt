package com.mnsons.offlinebank.ui.main.accountbalance

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.FragmentAccountBalanceBinding
import com.mnsons.offlinebank.ui.commons.adapters.AccountBalanceAdapter

class AccountBalanceFragment(private val accountBalances: List<String>) : DialogFragment() {

    private lateinit var _binding: FragmentAccountBalanceBinding

    private var dialogView: View? = null

    private var onCloseClickListener: (()->Unit)? = null

    private val accountBalanceAdapter by lazy {
        AccountBalanceAdapter().apply {
            all = accountBalances
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.Theme_FullScreenDialog_Slide)
            disableBackClick()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentAccountBalanceBinding.inflate(inflater)

        disableBackClick()

        return _binding.root
    }

    private fun disableBackClick() {
        dialogView?.isFocusableInTouchMode = true
        dialogView?.requestFocus()
        dialogView?.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dismiss()
                }
            }
            return@setOnKeyListener false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnClose.setOnClickListener {
            onCloseClickListener?.invoke()
            dismiss()
        }

        _binding.rvAccountBalances.adapter = accountBalanceAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onCloseClickListener = null
    }

    companion object {

        private const val TAG: String = "balance_dialog"
        private const val BALANCES = "BALANCES"

        fun display(
            fragmentManager: FragmentManager,
            accountBalances: List<String>,
            onCloseClickListener: (()->Unit)
        ): AccountBalanceFragment {
            val dialogFragment = AccountBalanceFragment(accountBalances)
            dialogFragment.onCloseClickListener = onCloseClickListener
            dialogFragment.arguments = Bundle().apply {
                putStringArrayList(BALANCES, ArrayList(accountBalances))
            }
            dialogFragment.show(fragmentManager, TAG)
            return dialogFragment
        }
    }

}
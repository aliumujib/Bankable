package com.mnsons.offlinebank.ui.commons.dialogs

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.LayoutTransactionOutcomeBinding


class TransactionStatusDialog : DialogFragment() {

    private lateinit var _binding: LayoutTransactionOutcomeBinding
    private var dialogView: View? = null

    private var onCloseClickListener: (()->Unit)? = null

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutTransactionOutcomeBinding.inflate(inflater)

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

        val status = arguments?.getBoolean(_STATUS) ?: false
        val messages = arguments?.getString(_MESSAGE)

        if (status) {
            _binding.image.setImageResource(R.drawable.ic_transaction_success)
        } else {
            _binding.image.setImageResource(R.drawable.ic_transaction_failure)
        }

        _binding.textView2.text = messages

        _binding.btnClose.setOnClickListener {
            onCloseClickListener?.invoke()
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        onCloseClickListener = null
    }

    companion object {

        private const val TAG: String = "success_dialog"
        private const val _STATUS: String = "_STATUS"
        private const val _MESSAGE: String = "_MESSAGE"

        fun display(
            fragmentManager: FragmentManager,
            status: Boolean,
            message: String,
            onCloseClickListener: (()->Unit)
        ): TransactionStatusDialog {
            val successFailureDialog = TransactionStatusDialog()
            successFailureDialog.onCloseClickListener = onCloseClickListener
            successFailureDialog.arguments = Bundle().apply {
                putString(_MESSAGE, message)
                putBoolean(_STATUS, status)
            }
            successFailureDialog.show(fragmentManager, TAG)
            return successFailureDialog
        }
    }
}

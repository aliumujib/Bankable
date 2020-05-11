package com.mnsons.offlinebank.ui.commons.dialogs

import android.os.Bundle
import android.view.View
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BankMenuModel
import com.mnsons.offlinebank.ui.commons.adapters.BankMenuAdapter
import com.mnsons.offlinebank.ui.commons.adapters.SelectionListener
import com.mnsons.offlinebank.ui.commons.base.BaseRoundedBottomSheetDialogFragment
import com.mnsons.offlinebank.utils.ext.slightDelay
import kotlinx.android.synthetic.main.layout_select_bank_bottom_sheet.*

class SelectFromMenuBottomSheet(
    private val banks: List<BankMenuModel>,
    private val selectBankListener: (BankMenuModel) -> Unit
) : BaseRoundedBottomSheetDialogFragment(), SelectionListener<BankMenuModel> {

    private val bankSelectionAdapter by lazy {
        BankMenuAdapter(
            this
        ).apply {
            all = banks
        }
    }

    override fun getLayoutRes(): Int = R.layout.layout_select_bank_bottom_sheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBanks.adapter = bankSelectionAdapter
    }

    override fun select(item: BankMenuModel) {
        dismiss()

        slightDelay({
            selectBankListener.invoke(item)
        }, 200)
    }

    override fun deselect(item: BankMenuModel) {

    }

}
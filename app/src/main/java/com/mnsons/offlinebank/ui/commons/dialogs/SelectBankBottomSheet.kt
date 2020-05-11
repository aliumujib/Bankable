package com.mnsons.offlinebank.ui.commons.dialogs

import android.os.Bundle
import android.view.View
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.bank.BankModel
import com.mnsons.offlinebank.ui.commons.adapters.bank.BankSelectionAdapter
import com.mnsons.offlinebank.ui.commons.adapters.bank.BankSelectionListener
import com.mnsons.offlinebank.ui.commons.base.BaseRoundedBottomSheetDialogFragment
import com.mnsons.offlinebank.utils.ext.delayForASecond
import kotlinx.android.synthetic.main.layout_select_bank_bottom_sheet.*

class SelectBankBottomSheet(
    private val banks: List<BankModel>,
    private val selectBankListener: (BankModel) -> Unit
) : BaseRoundedBottomSheetDialogFragment(),
    BankSelectionListener<BankModel> {

    private val bankSelectionAdapter by lazy {
        BankSelectionAdapter(
            BankSelectionAdapter.ViewType.SELECTABLE,
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

    override fun select(item: BankModel) {
        dismiss()

        delayForASecond {
            selectBankListener.invoke(item)
        }
    }

    override fun deselect(item: BankModel) {

    }

}
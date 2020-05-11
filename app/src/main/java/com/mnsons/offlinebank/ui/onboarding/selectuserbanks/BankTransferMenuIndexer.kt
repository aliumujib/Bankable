package com.mnsons.offlinebank.ui.onboarding.selectuserbanks

import androidx.fragment.app.Fragment
import com.mnsons.offlinebank.contracts.access.FetchAccessBankOtherBanksContract
import com.mnsons.offlinebank.contracts.gtb.FetchGTBankOtherBanksFirstPageContract
import com.mnsons.offlinebank.model.BankMenuModel
import com.mnsons.offlinebank.model.BankModel
import javax.inject.Inject


class BankTransferMenuIndexer @Inject constructor(
    private val successAction: (bankId: Int, List<BankMenuModel>) -> Unit,
    private val finishedAction: () -> Unit,
    val fragment: Fragment
) {

    private var selectedBanks = listOf<BankModel>()
    private var currentIndex: Int = 0

    private val gtBankMenuCall =
        fragment.registerForActivityResult(FetchGTBankOtherBanksFirstPageContract()) { result ->
            result.data?.let {
                successAction.invoke(selectedBanks[currentIndex].id, it)
            }
            runNextMenuCall()
        }

    private val accessBankMenuCall = fragment.registerForActivityResult(
        FetchAccessBankOtherBanksContract()
    ) { result ->
        result.data?.let {
            successAction.invoke(selectedBanks[currentIndex].id, it)
        }
        runNextMenuCall()
    }

    private fun runNextMenuCall() {
        currentIndex += 1
        if (currentIndex <= selectedBanks.lastIndex) {
            runContractForBank(selectedBanks[currentIndex])
        } else {
            currentIndex = 0
            selectedBanks = emptyList()
            finishedAction.invoke()
        }
    }


    private fun runContractForBank(bankModel: BankModel) {
        if (bankModel.id == 1) {
            gtBankMenuCall.launch(Unit)
        } else if (bankModel.id == 2) {
            accessBankMenuCall.launch(Unit)
        }
    }

    fun indexMenuForBanks(selectedBanks: List<BankModel>) {
        this.selectedBanks = selectedBanks
        runContractForBank(this.selectedBanks[0])
    }

}
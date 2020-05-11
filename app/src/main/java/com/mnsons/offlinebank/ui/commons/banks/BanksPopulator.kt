package com.mnsons.offlinebank.ui.commons.banks

import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.bank.BankModel

object BanksPopulator {

    fun fetchSupportedBanks(): List<BankModel> {
        val gtBank = BankModel(
            R.string.gtbank,
            1,
            0,
            "111",
            R.drawable.ic_gtbank
        )

        val accessBank = BankModel(
            R.string.accessbank,
            2,
            0,
            "112",
            R.drawable.ic_access
        )

        val zenithBank = BankModel(
            R.string.zenith_bank,
            3,
            0,
            "113",
            R.drawable.ic_zenith
        )

        val uba = BankModel(
            R.string.uba,
            4,
            0,
            "114",
            R.drawable.ic_uba
        )

        return mutableListOf(gtBank, accessBank, zenithBank, uba)
    }

}
package com.mnsons.offlinebank.ui.commons.banks

import android.content.Context
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BankModel
import javax.inject.Inject

class BanksPopulator {

    companion object {

        fun fetchSupportedBanks(): List<BankModel> {
            val gtbank = BankModel(
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
                2,
                0,
                "112",
                R.drawable.ic_zenith
            )

            val uba = BankModel(
                R.string.uba,
                2,
                0,
                "112",
                R.drawable.ic_uba
            )

            return mutableListOf(gtbank, accessBank, zenithBank, uba)
        }

    }

}
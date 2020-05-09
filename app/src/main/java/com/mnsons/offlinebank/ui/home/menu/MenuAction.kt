package com.mnsons.offlinebank.ui.home.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mnsons.offlinebank.R

sealed class MenuAction(
    val id: Int, @StringRes val titleRes: Int, @DrawableRes val iconRes: Int,
    val isEnabled: Boolean = false
) {
    object TransferFunds : MenuAction(1, R.string.transfer_funds, R.drawable.transfer, true)
    object BuyAirtime : MenuAction(2, R.string.buy_airtime, R.drawable.ic_airtime, true)
    object BuyData : MenuAction(3, R.string.buy_internet, R.drawable.ic_internet, true)
    object PayBills : MenuAction(4, R.string.pay_bills, R.drawable.ic_bills, false)
    object CheckAccountBalance :
        MenuAction(5, R.string.check_balance, R.drawable.ic_profile_circle, true)
}
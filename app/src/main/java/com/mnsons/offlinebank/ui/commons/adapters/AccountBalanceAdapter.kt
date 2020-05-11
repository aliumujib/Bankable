package com.mnsons.offlinebank.ui.commons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.R
import kotlinx.android.synthetic.main.item_account_balance.view.*

class AccountBalanceAdapter(
    private val selectionListener: SelectionListener<String>? = null
) : RecyclerView.Adapter<AccountBalanceAdapter.AccountBalanceItemViewHolder>() {

    var all: List<String> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountBalanceItemViewHolder {
        return AccountBalanceItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_account_balance, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return all.size
    }

    override fun onBindViewHolder(holderMenu: AccountBalanceItemViewHolder, position: Int) {
        holderMenu.bind(all[position])
    }

    inner class AccountBalanceItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(balance: String) {
            itemView.tvBankNameAndBalance.text = balance
        }

    }

}
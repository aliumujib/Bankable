package com.mnsons.offlinebank.ui.commons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BankMenuModel
import kotlinx.android.synthetic.main.item_bank.view.*

class BankMenuAdapter(
    private val selectionListener: SelectionListener<BankMenuModel>? = null
) : RecyclerView.Adapter<BankMenuAdapter.BankMenuItemViewHolder>() {

    var all: List<BankMenuModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankMenuItemViewHolder {
        return BankMenuItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return all.size
    }

    override fun onBindViewHolder(holderMenu: BankMenuItemViewHolder, position: Int) {
        holderMenu.bind(all[position])
    }

    inner class BankMenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bank: BankMenuModel) {
            itemView.tvBankName.text = bank.bankName
            itemView.ivBankLogo.setImageResource(R.drawable.ic_bank_logo)
            itemView.setOnClickListener {
                selectionListener?.select(bank)
            }
        }
    }

}
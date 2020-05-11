package com.mnsons.offlinebank.ui.commons.adapters.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.transaction.TransactionModel
import com.mnsons.offlinebank.utils.TransactionUtil
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionItemViewHolder>() {

    private val transactions = mutableListOf<TransactionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        return TransactionItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    fun setTransactions(items: List<TransactionModel>) {
        transactions.clear()
        transactions.addAll(items)
        notifyDataSetChanged()
    }

    inner class TransactionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(transaction: TransactionModel) {
            itemView.ivTypeIcon.setImageResource(TransactionUtil.getIconByType(transaction.type))
            itemView.tvType.text = transaction.type.value
            itemView.tvBankName.text = transaction.bank
            itemView.tvAmount.text = transaction.amount.toString()
        }

    }
}
package com.mnsons.offlinebank.ui.commons.adapters.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.transaction.SectionedTransactionModel
import kotlinx.android.synthetic.main.item_sectioned_transaction.view.*

class SectionedTransactionsAdapter(
    private val transactionsAdapter: TransactionsAdapter
) : RecyclerView.Adapter<SectionedTransactionsAdapter.SectionedTransactionItemViewHolder>() {

    private val sectionedTransactions = mutableListOf<SectionedTransactionModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionedTransactionItemViewHolder {
        return SectionedTransactionItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sectioned_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int = sectionedTransactions.size

    override fun onBindViewHolder(holder: SectionedTransactionItemViewHolder, position: Int) {
        holder.bind(sectionedTransactions[position])
    }

    fun setTransactions(items: List<SectionedTransactionModel>) {
        sectionedTransactions.clear()
        sectionedTransactions.addAll(items)
        notifyDataSetChanged()
    }

    inner class SectionedTransactionItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(sectionedTransaction: SectionedTransactionModel) {
            itemView.tvDay.text = sectionedTransaction.day

            val totalAmount = sectionedTransaction.transactions.sumByDouble { it.amount }
            val transactionsCount = sectionedTransaction.transactions.size

            itemView.tvDescription.text = "You spent $totalAmount on $transactionsCount Transactions"

            itemView.rvTransactions.adapter = transactionsAdapter
            transactionsAdapter.setTransactions(sectionedTransaction.transactions)
        }

    }
}
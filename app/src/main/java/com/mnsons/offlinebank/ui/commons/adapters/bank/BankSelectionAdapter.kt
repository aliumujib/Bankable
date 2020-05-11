package com.mnsons.offlinebank.ui.commons.adapters.bank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.bank.BankModel
import kotlinx.android.synthetic.main.item_bank.view.*

class BankSelectionAdapter(
    private val viewType: ViewType = ViewType.NORMAL,
    private val bankSelectionListener: BankSelectionListener<BankModel>? = null
) : RecyclerView.Adapter<BankSelectionAdapter.BankItemViewHolder>() {

    var all: List<BankModel> = mutableListOf()
    var backUp: List<BankModel> = mutableListOf()
    var selected: MutableList<BankModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankItemViewHolder {
        return BankItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return all.size
    }

    override fun onBindViewHolder(holder: BankItemViewHolder, position: Int) {
        var isSelected = false

        if (selected.contains(all[position])) {
            isSelected = true
        }

        holder.bind(all[position], isSelected)
    }

    fun addToSelected(item: BankModel) {
        selected.add(item)
        notifyDataSetChanged()
    }

    fun removeFromSelected(item: BankModel) {
        selected.remove(item)
        notifyDataSetChanged()
    }

    fun filter(newText: String) {

    }

    inner class BankItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bank: BankModel, isSelected: Boolean) {
            itemView.tvBankName.setText(bank.bankName)
            itemView.ivBankLogo.setImageResource(bank.bankLogo)

            if (viewType == ViewType.SELECTABLE) {
                itemView.ivCheckMark.isSelected = isSelected

                itemView.itemBankContainer.strokeColor = if (isSelected) {
                    ContextCompat.getColor(itemView.context, R.color.blue)
                } else {
                    ContextCompat.getColor(itemView.context, R.color.greyTransparent)
                }

                itemView.setOnClickListener {
                    if (isSelected) {
                        bankSelectionListener?.deselect(bank)
                    } else {
                        bankSelectionListener?.select(bank)
                    }
                }
            } else {
                itemView.ivCheckMark.visibility = View.INVISIBLE
                itemView.ivEllipses.visibility = View.VISIBLE
            }

        }
    }

    enum class ViewType {
        NORMAL,
        SELECTABLE
    }
}
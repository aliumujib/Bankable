package com.mnsons.offlinebank.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.model.BankModel
import com.mnsons.offlinebank.utils.MultiSelectListener
import kotlinx.android.synthetic.main.item_bank.view.*

class BankSelectionAdapter(
    private val viewType: ViewType = ViewType.NORMAL,
    private val multiSelectListener: MultiSelectListener<BankModel>? = null
) : RecyclerView.Adapter<BankSelectionAdapter.BankItemViewHolder>() {

    var all: MutableList<BankModel> = mutableListOf()
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

    inner class BankItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bank: BankModel, isSelected: Boolean) {
            itemView.tvBankName.text = bank.bankName

            if (viewType == ViewType.SELECTABLE) {
                itemView.ivCheckMark.isSelected = isSelected

                itemView.itemBankContainer.strokeColor = if (isSelected) {
                    ContextCompat.getColor(itemView.context, R.color.blue)
                } else {
                    ContextCompat.getColor(itemView.context, R.color.greyTransparent)
                }

                itemView.setOnClickListener {
                    if (isSelected) {
                        multiSelectListener?.deselect(bank)
                    } else {
                        multiSelectListener?.select(bank)
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
package com.mnsons.offlinebank.ui.main.home.menu

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnsons.offlinebank.databinding.MenuItemCardBinding
import com.mnsons.offlinebank.utils.ext.recursivelyApplyToChildren

class MenuAdapter constructor(private val menuActionClickListener: MenuActionClickListener) :
    ListAdapter<MenuAction, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            MenuItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuActionViewHolder(binding, menuActionClickListener)
    }

    fun isEmpty() = super.getItemCount() == 0


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MenuActionViewHolder).bind(getItem(position))
    }


    class MenuActionViewHolder(
        private val binding: MenuItemCardBinding,
        private val actionClickListener: MenuActionClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MenuAction) {
            itemView.setOnClickListener {
                actionClickListener.onMenuActionClick(model)
            }
            binding.parent.recursivelyApplyToChildren {
                it.isEnabled = model.isEnabled
            }
            binding.icon.setImageDrawable(itemView.context.getDrawable(model.iconRes))
            binding.title.text = Html.fromHtml(itemView.context.getString(model.titleRes))
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<MenuAction>() {
        override fun areItemsTheSame(oldItem: MenuAction, newItem: MenuAction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MenuAction,
            newItem: MenuAction
        ): Boolean {
            return oldItem == newItem
        }
    }
}
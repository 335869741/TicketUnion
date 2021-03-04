package zzz.bing.ticketunion.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zzz.bing.ticketunion.databinding.ItemChoicenessContentBinding
import zzz.bing.ticketunion.model.domain.UatmTbkItem

class ChoicenessContentAdapter:ListAdapter<UatmTbkItem,ChoicenessContentViewHolder>(
    object :DiffUtil.ItemCallback<UatmTbkItem>(){
        override fun areItemsTheSame(oldItem: UatmTbkItem, newItem: UatmTbkItem): Boolean {
            return oldItem.numIid == newItem.numIid
        }

        override fun areContentsTheSame(oldItem: UatmTbkItem, newItem: UatmTbkItem): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoicenessContentViewHolder {
        val binding = ItemChoicenessContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ChoicenessContentViewHolder(binding)
        viewHolder.itemView.setOnClickListener {  }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ChoicenessContentViewHolder, position: Int) {

    }
}
class ChoicenessContentViewHolder(binding: ItemChoicenessContentBinding):RecyclerView.ViewHolder(binding.root){

}
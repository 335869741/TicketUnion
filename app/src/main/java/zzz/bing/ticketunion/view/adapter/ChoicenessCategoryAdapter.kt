package zzz.bing.ticketunion.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ItemChoicenessCategoryBinding
import zzz.bing.ticketunion.model.domain.ChoicenessCategory
import zzz.bing.ticketunion.viewmodel.ChoicenessViewModel

class ChoicenessCategoryAdapter(val viewModel: ChoicenessViewModel):ListAdapter<ChoicenessCategory,ChoicenessCategoryViewHolder>(
    object :DiffUtil.ItemCallback<ChoicenessCategory>(){
        override fun areItemsTheSame(
            oldItem: ChoicenessCategory, newItem: ChoicenessCategory
        ): Boolean {
            return oldItem.favoritesId == newItem.favoritesId
        }
        override fun areContentsTheSame(
            oldItem: ChoicenessCategory, newItem: ChoicenessCategory
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    private var _onCheckedId = 0
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ChoicenessCategoryViewHolder {
        val binding = ItemChoicenessCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder = ChoicenessCategoryViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            if (_onCheckedId != viewHolder.adapterPosition){
                _onCheckedId = viewHolder.adapterPosition
                viewModel.choicenessItemPositionChange(_onCheckedId)
                notifyDataSetChanged()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ChoicenessCategoryViewHolder, position: Int) {
        holder.binding.textChoicenessCategory.text = getItem(position).favoritesTitle
        holder.binding.root.setBackgroundColor(holder.itemView.context.getColor(R.color.white))
        if (position == _onCheckedId){
            holder.binding.root.setBackgroundColor(holder.itemView.context.getColor(R.color.choicenessCategory))
        }
    }
}
class ChoicenessCategoryViewHolder(val binding:ItemChoicenessCategoryBinding)
    :RecyclerView.ViewHolder(binding.root)
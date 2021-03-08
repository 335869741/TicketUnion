package zzz.bing.ticketunion.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.databinding.ItemHomePagerLooperBinding
import zzz.bing.ticketunion.model.domain.ItemContent
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.UrlUtils

@Suppress("unused")
class HomePagerItemLooperAdapter : ListAdapter<ItemContent, HomePagerItemLooperViewHolder>(
    object : DiffUtil.ItemCallback<ItemContent>() {
        override fun areItemsTheSame(oldItem: ItemContent, newItem: ItemContent): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: ItemContent, newItem: ItemContent): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePagerItemLooperViewHolder {
        val binding =
            ItemHomePagerLooperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = HomePagerItemLooperViewHolder(binding)
        viewHolder.itemView
        return viewHolder
    }

    override fun onBindViewHolder(holder: HomePagerItemLooperViewHolder, position: Int) {
        val item = getItem(position)
        val width = holder.binding.imageView.width
        val height = holder.binding.imageView.height
        val url = UrlUtils.dynamicLoadingUrl(width,height,item.pictUrl)
        LogUtils.d(this,"url ==> $url")

        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.binding.imageView)
    }



}

class HomePagerItemLooperViewHolder(val binding: ItemHomePagerLooperBinding) : RecyclerView.ViewHolder(binding.root)
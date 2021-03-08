package zzz.bing.ticketunion.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ItemOnSellContentBinding
import zzz.bing.ticketunion.model.domain.OnSellMapData
import zzz.bing.ticketunion.utils.ActionActivity
import zzz.bing.ticketunion.utils.UrlUtils
import java.text.DecimalFormat

class OnSellAdapter:ListAdapter<OnSellMapData,OnSellViewHolder>(
    object :DiffUtil.ItemCallback<OnSellMapData>(){
        override fun areItemsTheSame(oldItem: OnSellMapData, newItem: OnSellMapData): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: OnSellMapData, newItem: OnSellMapData): Boolean {
            return oldItem == newItem
        }
    }
) {
    @Suppress("UselessCallOnNotNull")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnSellViewHolder {
        val binding = ItemOnSellContentBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        val viewHolder = OnSellViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val context = viewHolder.itemView.context
            val item = getItem(viewHolder.adapterPosition)
            ActionActivity.actionTicketActivity(context,item)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: OnSellViewHolder, position: Int) {
        val content = holder.itemView.context
        val item = getItem(position)
        val url = UrlUtils.urlJoinHttp(item.pictUrl)
        val binding = holder.binding

        binding.textTitle.text = item._title
        val amount = item.couponAmount.toDouble()
        val price = DecimalFormat("0.0").format(item.zkFinalprice.toDouble() - amount)
        val string = content.getString(R.string.onSellPrice,item.zkFinalprice,price)
        binding.textPrice.text = string

        Glide.with(content)
            .load(url)
            .into(holder.binding.imageOnSellIcon)
    }
}
class OnSellViewHolder(val binding:ItemOnSellContentBinding):RecyclerView.ViewHolder(binding.root)
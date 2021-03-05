package zzz.bing.ticketunion.view.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ItemOnSellContentBinding
import zzz.bing.ticketunion.model.domain.OnSellMapData
import zzz.bing.ticketunion.model.domain.TicketParcelable
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.TsUtils
import zzz.bing.ticketunion.utils.UrlUtils
import zzz.bing.ticketunion.view.activity.TicketActivity
import java.text.DecimalFormat

class OnSellAdapter():ListAdapter<OnSellMapData,OnSellViewHolder>(
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
            val item = getItem(viewHolder.adapterPosition)
            val context = viewHolder.itemView.context
            var url = item.couponClickUrl
            if (item.couponClickUrl.isNullOrEmpty()){
                TsUtils.ts(context,Constant.TOAST_PROMPT)
                url = item.clickUrl
            }
            val bundle = Bundle()
            bundle.putParcelable(Constant.KEY_TICKET_PARCELABLE,TicketParcelable(url, item.title, item.pictUrl))
            val intent = Intent(context, TicketActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: OnSellViewHolder, position: Int) {
        val content = holder.itemView.context
        val item = getItem(position)
        val url = UrlUtils.urlJoinHttp(item.pictUrl)
        val binding = holder.binding

        binding.textTitle.text = item.title
        val amount = item.couponAmount.toDouble()
        val price = DecimalFormat("0.0").format(item.zkFinalprice.toDouble() - amount)
        val string = content.getString(R.string.onSellPrice,item.zkFinalprice,price)
        binding.textPrice.text = string

        Glide.with(content)
            .load(url)
            .into(holder.binding.imageOnSellIcon)
    }
}
class OnSellViewHolder(val binding:ItemOnSellContentBinding):RecyclerView.ViewHolder(binding.root){

}
package zzz.bing.ticketunion.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.databinding.ItemChoicenessContentBinding
import zzz.bing.ticketunion.model.domain.ChoicenessContentMapData
import zzz.bing.ticketunion.utils.ActionActivity
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.UrlUtils

@Suppress("unused")
class ChoicenessContentAdapter(private val activity: FragmentActivity):ListAdapter<ChoicenessContentMapData,ChoicenessContentViewHolder>(
    object :DiffUtil.ItemCallback<ChoicenessContentMapData>(){
        override fun areItemsTheSame(oldItem: ChoicenessContentMapData, newItem: ChoicenessContentMapData): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: ChoicenessContentMapData, newItem: ChoicenessContentMapData): Boolean {
            return oldItem == newItem
        }
    }
) {
    @Suppress("UselessCallOnNotNull")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoicenessContentViewHolder {
        val binding = ItemChoicenessContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ChoicenessContentViewHolder(binding)
        viewHolder.binding.textCheck.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            val context = viewHolder.itemView.context
            ActionActivity.actionTicketActivity(context,item)
        }
        return viewHolder
    }

    @Suppress("UselessCallOnNotNull")
    override fun onBindViewHolder(holder: ChoicenessContentViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        if (item.pictUrl.isNullOrEmpty()){
            binding.textPrice.visibility = View.GONE
            binding.textWarning.visibility = View.VISIBLE
            return
        }
        val url = UrlUtils.urlJoinHttp(item.pictUrl) //UrlUtils.dynamicLoadingUrl(binding.imageIcon.width,item.pictUrl)
//        val context = holder.itemView.context
        LogUtils.d(this,"url ==> $url")
        if (item.couponClickUrl.isNullOrEmpty()){
            binding.textPrice.visibility = View.GONE
            binding.textWarning.visibility = View.VISIBLE
        }else{
            binding.textPrice.text = item.couponInfo
        }
        binding.textTitle.text = item._title
//        binding.textPrice.text = item.coupon_infocontext.getString(R.string.buttonChoicenessBuy,item.reserve_price)
//        binding.textPreferential.text = context.getString(R.string.textPreferentialPrompt,item.zk_final_price)
        Glide.with(holder.itemView.context)
            .load(url)
            .into(binding.imageIcon)
    }
}
class ChoicenessContentViewHolder(val binding: ItemChoicenessContentBinding):RecyclerView.ViewHolder(binding.root)
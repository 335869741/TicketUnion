package zzz.bing.ticketunion.view.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.databinding.ItemChoicenessContentBinding
import zzz.bing.ticketunion.model.domain.ChoicenessContentMapData
import zzz.bing.ticketunion.model.domain.TicketParcelable
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.UrlUtils
import zzz.bing.ticketunion.view.activity.TicketActivity

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
            var url = item.clickUrl
            if (item.couponClickUrl.isNullOrEmpty()){
                Toast.makeText(viewHolder.itemView.context,"来晚了，优惠券领完了！", Toast.LENGTH_SHORT).show()
            }else{
                url = item.couponClickUrl
            }
            val title = item.title
            val picUrl = item.pictUrl
            val bundle = Bundle()
            bundle.putParcelable(
                Constant.KEY_TICKET_PARCELABLE,
                TicketParcelable(url,title,picUrl)
            )
            val intent = Intent(activity, TicketActivity::class.java)
            intent.putExtras(bundle)
            activity.startActivity(intent)
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
        binding.textTitle.text = item.title
//        binding.textPrice.text = item.coupon_infocontext.getString(R.string.buttonChoicenessBuy,item.reserve_price)
//        binding.textPreferential.text = context.getString(R.string.textPreferentialPrompt,item.zk_final_price)
        Glide.with(holder.itemView.context)
            .load(url)
            .into(binding.imageIcon)
    }
}
class ChoicenessContentViewHolder(val binding: ItemChoicenessContentBinding):RecyclerView.ViewHolder(binding.root){

}
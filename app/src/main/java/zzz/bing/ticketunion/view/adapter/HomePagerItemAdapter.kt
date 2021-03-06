package zzz.bing.ticketunion.view.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ItemHomePagerBinding
import zzz.bing.ticketunion.model.domain.ItemContent
import zzz.bing.ticketunion.utils.*
import java.text.DecimalFormat


@Suppress("UNUSED_PARAMETER")
class HomePagerItemAdapter(activity: FragmentActivity) :
    ListAdapter<ItemContent, HomePagerItemViewHolder>(
        object : DiffUtil.ItemCallback<ItemContent>() {
            override fun areItemsTheSame(oldItem: ItemContent, newItem: ItemContent): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: ItemContent, newItem: ItemContent): Boolean {
                return oldItem == newItem
            }

        }
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagerItemViewHolder {
        val binding =
            ItemHomePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = HomePagerItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            val context = viewHolder.itemView.context
            ActionActivity.actionTicketActivity(context,item)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: HomePagerItemViewHolder, position: Int) {
//        (holder.binding as ItemHomePagerBinding).
        val item = getItem(position)
        holder.binding.textItemTitle.text = item._title
        //学习使用SpannableString来定义一个textView中不同分段的样式
        val text = " 省 ${item.couponAmount}元 "
//        val colorWhite = holder.itemView.context.getColor(R.color.white)
//        val colorRed = holder.itemView.context.getColor(R.color.red)
//        val start = text.indexOf(text)
//        val end = start + text.length
//        val spannable = SpannableStringBuilder(text)
//        val colorSpan = BackgroundColorSpan(Color.parseColor("#ffff0000"))
////        spannable.setSpan(
////                TextRoundBackground(colorRed,colorWhite,holder.itemView.context),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(colorSpan,start,end,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        holder.binding.textItemLabel.text = text
        holder.binding.textItemLabel.background = ContextCompat.getDrawable(
            holder.itemView.context,
            R.drawable.shape_item_label_background
        )
        holder.binding.textItemLabel.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                R.color.white
            )
        )
        val price = DecimalFormat("0.0").format(item.zkFinalPrice.toFloat() - item.couponAmount.toFloat())
        val spannableString = SpannableString("￥${price}")
        val colorSpan = ForegroundColorSpan(Color.parseColor("#F5A623"))
        spannableString.setSpan(
            colorSpan,
            0,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        holder.binding.textItemPrice.text = "券后  "
        holder.binding.textItemPrice.append(spannableString)
        holder.binding.textItemPrice.append("  ")
        val priceSpannable = SpannableString("￥${item.zkFinalPrice}")
        priceSpannable.setSpan(
            StrikethroughSpan(),
            0,
            priceSpannable.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        holder.binding.textItemPrice.append(priceSpannable)
        val count = DecimalFormat("0.0").format(item.volume.toDouble() / 10000)
        holder.binding.textItemCount.text = holder.itemView.context.getString(R.string.volumeCount, count)

        val width = holder.binding.imageItemIcon.width
        val height = holder.binding.imageItemIcon.height
        val url = UrlUtils.dynamicLoadingUrl(width, height, item.pictUrl)
//        LogUtils.d(this,"url ==> $url")

        Glide.with(holder.itemView.context)
            .load(url)
            .placeholder(R.drawable.ic_photo_placeholder)
            .into(holder.binding.imageItemIcon)
    }

}

class HomePagerItemViewHolder(val binding: ItemHomePagerBinding) : RecyclerView.ViewHolder(binding.root)
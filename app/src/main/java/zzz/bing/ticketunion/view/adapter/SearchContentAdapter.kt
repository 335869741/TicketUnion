package zzz.bing.ticketunion.view.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ItemSearchContentBinding
import zzz.bing.ticketunion.model.domain.SearchData
import java.text.DecimalFormat

class SearchContentAdapter: ListAdapter<SearchData,SearchContentViewHolder>(
    object : DiffUtil.ItemCallback<SearchData>(){
        override fun areItemsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchContentViewHolder {
        val binding = ItemSearchContentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder = SearchContentViewHolder(binding)
        viewHolder.itemView.setOnClickListener {

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchContentViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = getItem(position)
        holder.binding.textSearchTitle.text = item.title
        if (item.couponAmount.isNullOrEmpty()){
            holder.binding.textSearchPreferential.text = "来晚了已经领完了"
            holder.binding.textSearchPrice.text = "￥"
            holder.binding.textSearchPrice.append(item.zkFinalPrice)
        }else{
            holder.binding.textSearchPreferential.text = context.getString(R.string.textPreferentialPrompt,item.couponAmount)
            val price = DecimalFormat("0.0").format(item.zkFinalPrice.toFloat() - item.couponAmount.toFloat())
            val spannableString = SpannableString("￥$price")
            val colorSpan = ForegroundColorSpan(Color.parseColor("#F5A623"))
            spannableString.setSpan(
                colorSpan,
                0,
                spannableString.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            holder.binding.textSearchPrice.text = "券后  "
            holder.binding.textSearchPrice.append(spannableString)
            holder.binding.textSearchPrice.append("  ")
            val priceSpannable = SpannableString("￥${item.zkFinalPrice}")
            priceSpannable.setSpan(
                StrikethroughSpan(),
                0,
                priceSpannable.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            holder.binding.textSearchPrice.append(priceSpannable)
            holder.binding.textSearchPrice.append("   已售${item.volume}件")
        }

        Glide.with(context)
            .load(item.pictUrl)
            .into(holder.binding.imageSearchIcon)
    }
}

class SearchContentViewHolder(val binding: ItemSearchContentBinding): RecyclerView.ViewHolder(binding.root){}
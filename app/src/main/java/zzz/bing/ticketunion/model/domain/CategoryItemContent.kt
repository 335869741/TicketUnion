package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName
import zzz.bing.ticketunion.model.action.ITicketActivity

data class CategoryItemContent(
    @SerializedName("code")     val code: Int,
    @SerializedName("data")     val itemContentList: List<ItemContent>,
    @SerializedName("message")  val message: String,
    @SerializedName("success")  val success: Boolean
)

@Suppress("UselessCallOnNotNull")
data class ItemContent(
    @SerializedName("category_id")      val categoryId: Long,
    @SerializedName("category_name")    val categoryName: Any,
    @SerializedName("click_url")        val clickUrl: String,
    @SerializedName("commission_rate")  val commissionRate: String,
    @SerializedName("coupon_amount")    val couponAmount: Long,
    @SerializedName("coupon_click_url") val couponClickUrl: String,
    @SerializedName("coupon_end_time")  val couponEndTime: String,
    @SerializedName("coupon_info")      val couponInfo: Any,
    @SerializedName("coupon_remain_count")val couponRemainCount: Long,
    @SerializedName("coupon_share_url") val couponShareUrl: String,
    @SerializedName("coupon_start_fee") val couponStartFee: String,
    @SerializedName("coupon_start_time")val couponStartTime: String,
    @SerializedName("coupon_total_count")val couponTotalCount: Long,
    @SerializedName("item_description") val itemDescription: String,
    @SerializedName("item_id")          val itemId: Long,
    @SerializedName("level_one_category_id")val levelOneCategoryId: Long,
    @SerializedName("level_one_category_name")val levelOneCategoryName: String,
    @SerializedName("nick")             val nick: String,
    @SerializedName("pict_url")         val pictUrl: String,
    @SerializedName("seller_id")        val sellerId: Long,
    @SerializedName("shop_title")       val shopTitle: String,
    @SerializedName("small_images")     val smallImages: SmallImages,
    @SerializedName("title")            val _title: String,
    @SerializedName("user_type")        val userType: Int,
    @SerializedName("volume")           val volume: Long,
    @SerializedName("zk_final_price")   val zkFinalPrice: String
) : ITicketActivity {
    override fun getTitle(): String {
        return _title
    }

    override fun getUrl(): String {
        return if (isNoMore()) this.clickUrl else this.couponClickUrl
    }

    override fun getPic(): String {
        return pictUrl
    }

    override fun isNoMore(): Boolean {
        return this.couponClickUrl.isNullOrEmpty()
    }
}

data class SmallImages(
    @SerializedName("string")           val string: List<String>
)
package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName
import zzz.bing.ticketunion.model.action.ITicketActivity

data class ChoicenessContent(
    @SerializedName("code")val code: Int,
    @SerializedName("data")val content: ChoicenessContentData,
    @SerializedName("message")val message: String,
    @SerializedName("success")val success: Boolean
)

data class ChoicenessContentData(
    @SerializedName("tbk_dg_optimus_material_response")val content: ChoicenessContentResponse
)

data class ChoicenessContentResponse(
    @SerializedName("is_default")val isDefault: String,
    @SerializedName("request_id")val requestId: String,
    @SerializedName("result_list")val choicenessContentList: ChoicenessContentList,
    @SerializedName("total_count")val totalCount: Int
)

data class ChoicenessContentList(
    @SerializedName("map_data")val ContentListMapData: List<ChoicenessContentMapData>
)

data class ChoicenessContentMapData(
    @SerializedName("category_id")val categoryId: Int,
    @SerializedName("click_url")val clickUrl: String,
    @SerializedName("commission_rate")val commissionRate: String,
    @SerializedName("coupon_amount")val couponAmount: Int,
    @SerializedName("coupon_click_url")val couponClickUrl: String,
    @SerializedName("coupon_end_time")val couponEndTime: String,
    @SerializedName("coupon_info")val couponInfo: String,
    @SerializedName("coupon_remain_count")val couponRemainCount: Int,
    @SerializedName("coupon_share_url")val couponShareUrl: String,
    @SerializedName("coupon_start_fee")val couponStartFee: String,
    @SerializedName("coupon_start_time")val couponStartTime: String,
    @SerializedName("coupon_total_count")val couponTotalCount: Int,
    @SerializedName("item_id")val itemId: Long,
    @SerializedName("level_one_category_id")val levelOneCategoryId: Int,
    @SerializedName("nick")val nick: String,
    @SerializedName("pict_url")val pictUrl: String,
    @SerializedName("reserve_price")val reservePrice: String,
    @SerializedName("seller_id")val sellerId: Long,
    @SerializedName("shop_title")val shopTitle: String,
    @SerializedName("small_images")val smallImages: ChoicenessContentSsmallImages,
    @SerializedName("title")val _title: String,
    @SerializedName("user_type")val userType: Int,
    @SerializedName("volume")val volume: Int,
    @SerializedName("white_image")val white_image: String,
    @SerializedName("zk_final_price")val zkfinalPrice: String
) : ITicketActivity {
    override fun getTitle(): String {
        return _title
    }

    override fun getUrl(): String {
        return if (isNoMore()) this.clickUrl else this.couponClickUrl
    }

    override fun getPic(): String {
        return this.pictUrl
    }

    @Suppress("UselessCallOnNotNull")
    override fun isNoMore(): Boolean {
        return this.couponClickUrl.isNullOrEmpty()
    }
}

data class ChoicenessContentSsmallImages(
    @SerializedName("string")val string: List<String>
)
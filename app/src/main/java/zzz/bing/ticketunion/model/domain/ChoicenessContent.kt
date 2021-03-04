package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName

data class ChoicenessContent(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val ContentResponse: ChoicenessContentResponse,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)

data class ChoicenessContentResponse(
    @SerializedName("tbk_uatm_favorites_item_get_response")
    val tbkContentResponseResponse: TbkChoicenessContentResponseResponse
)

data class TbkChoicenessContentResponseResponse(
    @SerializedName("request_id") val requestId: String,
    @SerializedName("results") val results: Results,
    @SerializedName("total_results") val totalResults: Int
)

data class Results(
    @SerializedName("favoriteId") val favoriteId: Int,
    @SerializedName("uatm_tbk_item") val choicenessContentList: List<UatmTbkItem>
)

data class UatmTbkItem @JvmOverloads constructor(
    @SerializedName("click_url") val clickUrl: String = "",
    @SerializedName("coupon_click_url") val couponClickUrl: String = "",
    @SerializedName("coupon_end_time") val couponEndTime: String = "",
    @SerializedName("coupon_info") val couponInfo: String = "",
    @SerializedName("coupon_remain_count") val couponRemainCount: Int = 0,
    @SerializedName("coupon_start_time") val couponStartTime: String = "",
    @SerializedName("coupon_total_count") val couponTotalCount: Long = 0,
    @SerializedName("event_end_time") val eventEndTime: String = "",
    @SerializedName("event_start_time") val eventStartTime: String = "",
    @SerializedName("item_url") val itemUrl: String = "",
    @SerializedName("num_iid") val numIid: Long = 0,
    @SerializedName("pict_url") val pictUrl: String = "",
    @SerializedName("reserve_price") val reservePrice: String = "",
    @SerializedName("status") val status: Long = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("tk_rate") val tkRate: String = "",
    @SerializedName("type") val type: Long = 0,
    @SerializedName("user_type") val userType: Long = 0,
    @SerializedName("volume") val volume: Long = 0,
    @SerializedName("zk_final_price") val zkFinalPrice: String = "",
    @SerializedName("zk_final_price_wap") val zkFinalPriceWap: String = ""
)
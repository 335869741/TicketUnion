package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName

data class SearchPageContent(
    @SerializedName("code")val code: Int,
    @SerializedName("data")val SearchPageData: SearchPageData,
    @SerializedName("message")val message: String,
    @SerializedName("success")val success: Boolean
)

data class SearchPageData(
    @SerializedName("tbk_dg_material_optional_response")val searchPageDataResponse: SearchPageResponse
)

data class SearchPageResponse(
    @SerializedName("request_id")val requestId: String,
    @SerializedName("result_list")val resultList: SearchPageResultList,
    @SerializedName("total_results")val totalResults: Int
)

data class SearchPageResultList(
    @SerializedName("map_data")val searchPageDataList: List<SearchData>
)

data class SearchData(
    @SerializedName("category_id")val categoryId: Int,
    @SerializedName("category_name")val categoryName: String,
    @SerializedName("commission_rate")val commissionRate: String,
    @SerializedName("commission_type")val commissionType: String,
    @SerializedName("coupon_amount")val couponAmount: String,
    @SerializedName("coupon_end_time")val couponEndTime: String,
    @SerializedName("coupon_id")val couponId: String,
    @SerializedName("coupon_info")val couponInfo: String,
    @SerializedName("coupon_remain_count")val couponRemainCount: Int,
    @SerializedName("coupon_share_url")val couponShareUrl: String,
    @SerializedName("coupon_start_fee")val couponStartFee: String,
    @SerializedName("coupon_start_time")val couponStartTime: String,
    @SerializedName("coupon_total_count")val couponTotalCount: Int,
    @SerializedName("include_dxjh")val includeDxjh: String,
    @SerializedName("include_mkt")val includeMkt: String,
    @SerializedName("info_dxjh")val infoDxjh: String,
    @SerializedName("item_description")val itemDescription: String,
    @SerializedName("item_id")val itemId: Long,
    @SerializedName("item_url")val itemUrl: String,
    @SerializedName("jdd_num")val jddNum: Int,
    @SerializedName("jdd_price")val jddPrice: Any,
    @SerializedName("level_one_category_id")val levelOneCategoryId: Int,
    @SerializedName("level_one_category_name")val levelOneCategoryName: String,
    @SerializedName("nick")val nick: String,
    @SerializedName("num_iid")val numIid: Long,
    @SerializedName("oetime")val oetime: Any,
    @SerializedName("ostime")val ostime: Any,
    @SerializedName("pict_url")val pictUrl: String,
    @SerializedName("presale_deposit")val presaleDeposit: String,
    @SerializedName("presale_end_time")val presaleEndTime: Int,
    @SerializedName("presale_start_time")val presaleStartTime: Int,
    @SerializedName("presale_tail_end_time")val presaleTailEndTime: Int,
    @SerializedName("presale_tail_start_time")val presaleTailStartTime: Int,
    @SerializedName("provcity")val provcity: String,
    @SerializedName("real_post_fee")val realPostFee: String,
    @SerializedName("reserve_price")val reservePrice: String,
    @SerializedName("seller_id")val sellerId: Long,
    @SerializedName("shop_dsr")val shopDsr: Int,
    @SerializedName("shop_title")val shopTitle: String,
    @SerializedName("short_title")val shortTitle: String,
    @SerializedName("small_images")val smallImages: SearchDataSmallImages,
    @SerializedName("title")val title: String,
    @SerializedName("tk_total_commi")val tkTotalCommi: String,
    @SerializedName("tk_total_sales")val tkTotalSales: String,
    @SerializedName("url")val url: String,
    @SerializedName("user_type")val userType: Int,
    @SerializedName("volume")val volume: Int,
    @SerializedName("white_image")val whiteImage: String,
    @SerializedName("x_id")val xId: String,
    @SerializedName("zk_final_price")val zkFinalPrice: String
)

data class SearchDataSmallImages(
    val string: List<String>
)
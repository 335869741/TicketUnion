package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName

data class SearchRecommend(
    @SerializedName("code")val code: Int,
    @SerializedName("data")val searchRecommendList: List<SearchRecommendContent>,
    @SerializedName("message")val message: String,
    @SerializedName("success")val success: Boolean
)

data class SearchRecommendContent(
    @SerializedName("createTime")val createTime: String,
    @SerializedName("id")val id: String,
    @SerializedName("keyword")val keyword: String
)
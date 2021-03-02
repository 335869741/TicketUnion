package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("code")    val code: Int,
    @SerializedName("data")    val titles: List<Title>,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)

data class Title(
    @SerializedName("id")   val materialId: Int,
    @SerializedName("title")val titleName: String
)
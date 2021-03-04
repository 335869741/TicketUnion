package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName

data class Choiceness(
    @SerializedName("code")     val code: Int,
    @SerializedName("data")     val category: List<ChoicenessCategory>,
    @SerializedName("message")  val message: String,
    @SerializedName("success")  val success: Boolean
)

data class ChoicenessCategory(
    @SerializedName("favorites_id")     val favoritesId: Int,
    @SerializedName("favorites_title")  val favoritesTitle: String,
    @SerializedName("type")             val type: Int
)
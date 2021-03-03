package zzz.bing.ticketunion.model.domain

import com.google.gson.annotations.SerializedName

data class TicketTaoCode(
    @SerializedName("code")     val code: Int,
    @SerializedName("data")     val TaoCodeCreate: TaoCodeCreate,
    @SerializedName("message")  val message: String,
    @SerializedName("success")  val success: Boolean
)

data class TaoCodeCreate(
    @SerializedName("tbk_tpwd_create_response")val taoCodeCreateResponse: TaoCodeCreateResponse
)

data class TaoCodeCreateResponse(
    @SerializedName("data")         val taoCodeBody: TaoCodeBody,
    @SerializedName("request_id")   val request_id: String
)

data class TaoCodeBody(
    @SerializedName("model")val code: String
)
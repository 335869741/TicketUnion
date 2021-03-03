package zzz.bing.ticketunion.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import zzz.bing.ticketunion.model.domain.Category
import zzz.bing.ticketunion.model.domain.CategoryItemContent
import zzz.bing.ticketunion.model.domain.TicketTaoCode
import zzz.bing.ticketunion.model.domain.TicketTaoCodeParams

interface Api {

    @GET("discovery/categories")
    fun getCategory(): Call<Category>

    @GET
    fun getCategoryItemContent(@Url url:String):Call<CategoryItemContent>

    @POST("tpwd")
    fun postTicketTaoCode(@Body ticketTaoCodeParams: TicketTaoCodeParams):Call<TicketTaoCode>
}
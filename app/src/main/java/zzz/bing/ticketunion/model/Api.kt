package zzz.bing.ticketunion.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import zzz.bing.ticketunion.model.domain.Category
import zzz.bing.ticketunion.model.domain.CategoryItemContent

interface Api {

    @GET("discovery/categories")
    fun getCategory(): Call<Category>

    @GET
    fun getCategoryItemContent(@Url url:String):Call<CategoryItemContent>
}
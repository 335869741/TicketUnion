package zzz.bing.ticketunion.model

import retrofit2.Call
import retrofit2.http.*
import zzz.bing.ticketunion.model.domain.*

interface Api {

    /**
     * 获取首页推荐列表
     */
    @GET("discovery/categories")
    fun getCategory(): Call<Category>

    /**
     * 根据首页id获取内容
     */
    @GET
    fun getCategoryItemContent(@Url url: String): Call<CategoryItemContent>

    /**
     * 获取淘宝领券码
     */
    @POST("tpwd")
    fun postTicketTaoCode(@Body ticketTaoCodeParams: TicketTaoCodeParams): Call<TicketTaoCode>

    /**
     * 获取精选内容
     */
    @GET("recommend/categories")
    fun getChoicenessCategory(): Call<Choiceness>

    /**
     * 获取特惠列表
     */
    @GET
    fun getChoicenessContent(@Url url: String): Call<ChoicenessContent>

    /**
     *  根据特惠id获取内容
     */
    @GET
    fun getOnSellPage(@Url url: String): Call<OnSellContent>

    /**
     * 获取热搜词
     */
    @GET("search/recommend")
    fun getSearchRecommend(): Call<SearchRecommend>

//    @GET("search")
//    fun getSearchContent(@Query("page") page: Int, @Query("keyword ") keyword: String): Call<SearchPageContent>
//
//    @GET
//    fun getSearchContent(@Url url: String): Call<SearchPageContent>

    @GET("search")
    fun getSearchContent(@QueryMap params:Map<String,String>): Call<SearchPageContent>
}
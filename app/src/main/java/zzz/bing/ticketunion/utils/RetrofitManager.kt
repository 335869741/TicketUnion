package zzz.bing.ticketunion.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    companion object {
        private var instance :RetrofitManager? = null
        get() {
            if (field == null){
                field = RetrofitManager()
            }
            return field
        }

        @Synchronized
        fun get():RetrofitManager = instance!!
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            //毫秒
            .connectTimeout(Constant.TIME_OUT_CONTROL, TimeUnit.MILLISECONDS)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .build()
    }

}

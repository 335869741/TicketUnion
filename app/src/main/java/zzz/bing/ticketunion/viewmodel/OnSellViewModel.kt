package zzz.bing.ticketunion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import zzz.bing.ticketunion.model.Api
import zzz.bing.ticketunion.model.domain.OnSellContent
import zzz.bing.ticketunion.model.domain.OnSellMapData
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.RetrofitManager
import zzz.bing.ticketunion.utils.UrlUtils
import java.net.HttpURLConnection

class OnSellViewModel(application: Application): AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create<Api>()

    private var _onSellNetState = MutableLiveData<MainViewModel.NetLoadState>()
    private var _onSellData = MutableLiveData<List<OnSellMapData>>()
    private var _onSellPage = 0

    val onSellPage:Int = _onSellPage
    val onSellMapData:LiveData<List<OnSellMapData>> = _onSellData

    fun loadOnSell(){
        val url = UrlUtils.onSellPageUrl(_onSellPage)
        _api.getOnSellPage(url).enqueue(object : Callback<OnSellContent>{
            override fun onResponse(call: Call<OnSellContent>, response: Response<OnSellContent>) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK){
                    val onSellMapData = response.body()?.onSellData?.onSellDataResponse?.resultList?.mapData
                    _onSellData.postValue(onSellMapData)
                }
            }

            override fun onFailure(call: Call<OnSellContent>, t: Throwable) {
//                TODO("Not yet implemented")
            }
        })
    }
}
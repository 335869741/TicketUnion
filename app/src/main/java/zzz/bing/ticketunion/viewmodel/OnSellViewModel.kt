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
import zzz.bing.ticketunion.utils.*
import java.net.HttpURLConnection

class OnSellViewModel(application: Application) : AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create<Api>()

    private var _onSellNetState = MutableLiveData<NetLoadState>()
    private var _onSellData = MutableLiveData<List<OnSellMapData>>().also { loadOnSell() }
    private var _onSellPage = 1

    val onSellPage: Int = _onSellPage
    val onSellMapData: LiveData<List<OnSellMapData>> = _onSellData
    val onSellNetState: LiveData<NetLoadState> = _onSellNetState

    fun loadOnSell() {
        _onSellNetState.postValue(NetLoadState.Loading)
        val url = UrlUtils.onSellPageUrl(_onSellPage)
        _api.getOnSellPage(url).enqueue(object : Callback<OnSellContent> {
            override fun onResponse(call: Call<OnSellContent>, response: Response<OnSellContent>) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK) {
                    val onSellMapData = response.body()?.onSellData?.onSellDataResponse?.resultList?.mapData
                    LogUtils.d(this@OnSellViewModel, "onSellMapData ==> $onSellMapData")
                    _onSellPage++
                    _onSellData.postValue(onSellMapData)
                    _onSellNetState.postValue(NetLoadState.Successful)
                } else {
                    LogUtils.d(this@OnSellViewModel, "code ==> ${response.code()}")
                    _onSellNetState.postValue(NetLoadState.Error)
                }
            }

            override fun onFailure(call: Call<OnSellContent>, t: Throwable) {
                LogUtils.d(this@OnSellViewModel, "Throwable ==> $t")
                _onSellNetState.postValue(NetLoadState.Error)
            }
        })
    }
}
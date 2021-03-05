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
import zzz.bing.ticketunion.model.domain.Choiceness
import zzz.bing.ticketunion.model.domain.ChoicenessCategory
import zzz.bing.ticketunion.model.domain.ChoicenessContent
import zzz.bing.ticketunion.model.domain.ChoicenessContentMapData
import zzz.bing.ticketunion.utils.*
import java.net.HttpURLConnection

class ChoicenessViewModel(application: Application): AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create<Api>()

    private var _choicenessItemPosition = MutableLiveData<Int>()
    private var _choicenessContentNetState = MutableLiveData<NetLoadState>()
    private var _choicenessCategoryList = MutableLiveData<List<ChoicenessCategory>>()
    private var _choicenessContentList = MutableLiveData<List<ChoicenessContentMapData>>()

    val choicenessContentNetState: LiveData<NetLoadState> get() = _choicenessContentNetState
    val choicenessPositionItem: LiveData<Int> get() = _choicenessItemPosition
    val choicenessCategoryList: LiveData<List<ChoicenessCategory>> get() = _choicenessCategoryList
    val choicenessContentList: LiveData<List<ChoicenessContentMapData>> get() = _choicenessContentList

    fun choicenessItemPositionChange(itemPosition:Int){
        _choicenessItemPosition.postValue(itemPosition)
//        if (itemPosition >= 0 && itemPosition < _choicenessCategoryList.value?.size!!){
//        }
    }

    fun netLoadChoicenessCategory() {
        _api.getChoicenessCategory().enqueue(object : Callback<Choiceness> {
            override fun onResponse(call: Call<Choiceness>, response: Response<Choiceness>) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK
                ) {
                    val body = response.body()
                    _choicenessCategoryList.postValue(body?.category)
                }else{
                    netLoadChoicenessCategory()
                }
            }
            override fun onFailure(call: Call<Choiceness>, t: Throwable) {
                netLoadChoicenessCategory()
                LogUtils.d(this@ChoicenessViewModel, "Throwable ==> $t")
            }
        })

    }

    fun netLoadChoicenessContent(categoryId: Int) {
        _choicenessContentNetState.postValue(NetLoadState.Loading)
        val url:String = UrlUtils.choicenessContentUrl(categoryId)
        LogUtils.d(this,"url ==> $url")
        _api.getChoicenessContent(url).enqueue(object : Callback<ChoicenessContent> {
            override fun onResponse(
                call: Call<ChoicenessContent>, response: Response<ChoicenessContent>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK){
                    val content = response.body()?.content?.content?.choicenessContentList?.ContentListMapData
                    LogUtils.d(this@ChoicenessViewModel,"response ==> ${response.body()}")
                    LogUtils.d(this@ChoicenessViewModel,"content ==> $content")
                    _choicenessContentNetState.postValue(NetLoadState.Successful)
                    _choicenessContentList.postValue(content)
                }else{
                    _choicenessContentNetState.postValue(NetLoadState.Error)
                }
            }

            override fun onFailure(call: Call<ChoicenessContent>, t: Throwable) {
                LogUtils.d(this,"Throwable ==> $t")
                _choicenessContentNetState.postValue(NetLoadState.Error)
            }
        })
    }
}
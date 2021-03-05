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
import zzz.bing.ticketunion.model.domain.MapData
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.RetrofitManager
import zzz.bing.ticketunion.utils.UrlUtils
import java.net.HttpURLConnection

class ChoicenessViewModel(application: Application): AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create<Api>()

    private var _choicenessItemPosition = MutableLiveData<Int>()
    private var _choicenessContentNetState = MutableLiveData<MainViewModel.NetLoadState>()
    private var _choicenessCategoryList = MutableLiveData<List<ChoicenessCategory>>()
    private var _choicenessContentList = MutableLiveData<List<MapData>>()

    val choicenessContentNetState: LiveData<MainViewModel.NetLoadState> get() = _choicenessContentNetState
    val choicenessPositionItem: LiveData<Int> get() = _choicenessItemPosition
    val choicenessCategoryList: LiveData<List<ChoicenessCategory>> get() = _choicenessCategoryList
    val choicenessContentList: LiveData<List<MapData>> get() = _choicenessContentList

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
//                TODO("Not yet implemented")
                netLoadChoicenessCategory()
                LogUtils.d(this@ChoicenessViewModel, "Throwable ==> $t")
            }
        })

    }

    fun netLoadChoicenessContent(categoryId: Int) {
        _choicenessContentNetState.postValue(MainViewModel.NetLoadState.Loading)
        val url:String = UrlUtils.choicenessContentUrl(categoryId)
        LogUtils.d(this,"url ==> $url")
        _api.getChoicenessContent(url).enqueue(object : Callback<ChoicenessContent> {
            override fun onResponse(
                call: Call<ChoicenessContent>, response: Response<ChoicenessContent>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK){
                    val content = response.body()?.content?.tbk_dg_optimus_material_response?.result_list?.map_data
                    LogUtils.d(this@ChoicenessViewModel,"response ==> ${response.body()}")
                    LogUtils.d(this@ChoicenessViewModel,"content ==> $content")
                    _choicenessContentNetState.postValue(MainViewModel.NetLoadState.Successful)
                    _choicenessContentList.postValue(content)
                }else{
                    _choicenessContentNetState.postValue(MainViewModel.NetLoadState.Error)
                }
            }

            override fun onFailure(call: Call<ChoicenessContent>, t: Throwable) {
                LogUtils.d(this,"Throwable ==> $t")
                _choicenessContentNetState.postValue(MainViewModel.NetLoadState.Error)
            }
        })
    }
}
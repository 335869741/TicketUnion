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
import zzz.bing.ticketunion.model.domain.*
import zzz.bing.ticketunion.utils.*
import java.net.HttpURLConnection

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _homePageSave: Map<Int, List<ItemContent>>? = null
    private var _choicenessItemPosition = MutableLiveData<Int>()
    private val _choicenessContentNetState = MutableLiveData<NetLoadState>()
    //    private val _retrofit = RetrofitManager.get().retrofit
    private val _api = RetrofitManager.get().retrofit.create<Api>()
    private var _titles = MutableLiveData<List<Title>>().also { netLoadCategoryTitles() }
    private var _categoryTitleResponse =
        MutableLiveData<NetLoadState>().also { it.value = NetLoadState.Loading }
    private var _categoryItemResponse = MutableLiveData<Map<Int, NetLoadState>>()
    private var _categoryItemList = MutableLiveData<Map<Int, List<ItemContent>>>()
    private var _categoryLooperList = MutableLiveData<Map<Int, List<ItemContent>>>()
    private var _choicenessCategoryList = MutableLiveData<List<ChoicenessCategory>>()
    private var _choicenessContentList = MutableLiveData<List<MapData>>()

    val choicenessContentNetState:LiveData<NetLoadState> get() = _choicenessContentNetState
    val choicenessPositionItem:LiveData<Int> get() = _choicenessItemPosition
    val choicenessCategoryList: LiveData<List<ChoicenessCategory>> get() = _choicenessCategoryList
    val choicenessContentList: LiveData<List<MapData>> get() = _choicenessContentList
    val categoryLooperList: LiveData<Map<Int, List<ItemContent>>> get() = _categoryLooperList
    val categoryTitleResponse: LiveData<NetLoadState> get() = _categoryTitleResponse
    val categoryItemResponse: LiveData<Map<Int, NetLoadState>> get() = _categoryItemResponse
    val titles: LiveData<List<Title>> get() = _titles
    val categoryItemLiveData: LiveData<Map<Int, List<ItemContent>>> get() = _categoryItemList

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
                LogUtils.d(this@MainViewModel, "Throwable ==> $t")
            }
        })

    }

    fun netLoadChoicenessContent(categoryId: Int) {
        _choicenessContentNetState.postValue(NetLoadState.Loading)
        val url:String = UrlUtils.choicenessContentUrl(categoryId)
        LogUtils.d(this,"url ==> $url")
        _api.getChoicenessContent(url).enqueue(object : Callback<ChoicenessContent>{
            override fun onResponse(
                call: Call<ChoicenessContent>, response: Response<ChoicenessContent>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK){
                    val content = response.body()?.content?.tbk_dg_optimus_material_response?.result_list?.map_data
                    LogUtils.d(this@MainViewModel,"response ==> ${response.body()}")
                    LogUtils.d(this@MainViewModel,"content ==> $content")
                    _choicenessContentNetState.postValue(NetLoadState.Successful)
                    _choicenessContentList.postValue(content)
                }else{
                    _choicenessContentNetState.postValue(NetLoadState.Error)
                }
            }

            override fun onFailure(call: Call<ChoicenessContent>, t: Throwable) {
//                TODO("Not yet implemented")
                LogUtils.d(this,"Throwable ==> $t")
                _choicenessContentNetState.postValue(NetLoadState.Error)
            }
        })
    }

    fun netLoadCategoryItem(materialId: Int, pager: Int) {
        if (_homePageSave != null && _homePageSave?.containsKey(materialId)!!) {
            _categoryItemList.postValue(_homePageSave)
            _categoryItemResponse.postValue(mapOf(materialId to NetLoadState.Successful))
            return
        }
        val task = _api.getCategoryItemContent(UrlUtils.createCategoryItemUrl(materialId, pager))
        _categoryItemResponse.postValue(mapOf(materialId to NetLoadState.Loading))
        task.enqueue(object : Callback<CategoryItemContent> {
            override fun onResponse(
                call: Call<CategoryItemContent>,
                response: Response<CategoryItemContent>
            ) {
                val code = response.code()
                LogUtils.d(this@MainViewModel, "code ==> $code")
                if (code == HttpURLConnection.HTTP_OK) {
                    val itemContent = response.body()?.itemContentList
                    val mapItem = mapOf(materialId to itemContent!!)
                    _categoryItemList.postValue(mapItem)
//                    val subList = itemContent.subList(itemContent.size - 5, itemContent.size)
//                    _categoryLooperList.postValue(mapOf(materialId to subList))
                    _categoryItemResponse.postValue(mapOf(materialId to NetLoadState.Successful))
                    if (_homePageSave == null) {
                        _homePageSave = mapItem
                    }
                    LogUtils.d(this@MainViewModel, "请求成功 materialId == > $materialId")
                } else {
                    LogUtils.d(this@MainViewModel, "请求失败 == > $code")
                    _categoryItemResponse.postValue(mapOf(materialId to NetLoadState.Error))
                }
            }

            override fun onFailure(call: Call<CategoryItemContent>, t: Throwable) {
                LogUtils.d(this@MainViewModel, "请求出现意外 == > $t")
                _categoryItemResponse.postValue(mapOf(materialId to NetLoadState.Error))
            }
        })
    }

    private fun netLoadCategoryTitles() {
        val task = _api.getCategory()
        task.enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                val code = response.code()
                LogUtils.d(this@MainViewModel, "code ==> $code")
                if (code == HttpURLConnection.HTTP_OK) {
                    val category = response.body()
                    LogUtils.d(this@MainViewModel, "请求成功 category == > $category")
                    _titles.postValue(category?.titles)
                    _categoryTitleResponse.postValue(NetLoadState.Successful)
                } else {
                    LogUtils.d(this@MainViewModel, "请求失败 == > $code")
                    _categoryTitleResponse.postValue(NetLoadState.Error)
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                LogUtils.d(this@MainViewModel, "请求出现意外 == > $t")
                _categoryTitleResponse.postValue(NetLoadState.Error)
            }
        })
    }

    fun reLoadCategoryTitles() {
        if (_categoryTitleResponse.value == NetLoadState.Error) {
            netLoadCategoryTitles()
            _categoryTitleResponse.postValue(NetLoadState.Loading)
        }
    }

//    fun loadMore(materialId: Int, page: Int) {
//
//    }

}
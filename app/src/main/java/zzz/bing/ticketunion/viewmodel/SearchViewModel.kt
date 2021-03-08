package zzz.bing.ticketunion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zzz.bing.ticketunion.db.SearchHistoryRepository
import zzz.bing.ticketunion.db.entity.SearchHistory
import zzz.bing.ticketunion.model.Api
import zzz.bing.ticketunion.model.domain.SearchData
import zzz.bing.ticketunion.model.domain.SearchPageContent
import zzz.bing.ticketunion.model.domain.SearchRecommend
import zzz.bing.ticketunion.model.domain.SearchRecommendContent
import zzz.bing.ticketunion.utils.*
import java.net.HttpURLConnection

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create(Api::class.java)
    private val _searchHistoryRepository by lazy { SearchHistoryRepository() }

    private val _searchRecommend: MutableLiveData<List<SearchRecommendContent>> by lazy { MutableLiveData() }
    private val _searchContent: MutableLiveData<List<SearchData>> by lazy { MutableLiveData() }
    private val _searchHistory: LiveData<List<SearchHistory>> by lazy { _searchHistoryRepository.getAllSearchHistory() }
    private val _searchLoadState = MutableLiveData<NetLoadState>()

    val searchRecommend: LiveData<List<SearchRecommendContent>> get() = _searchRecommend
    val searchContent: LiveData<List<SearchData>> get() = _searchContent
    val searchHistory: LiveData<List<SearchHistory>> get() = _searchHistory
    val searchLoadState: LiveData<NetLoadState> get() = _searchLoadState
    var isLoadMore:Boolean = false

    private var _searchText: String? = null
    private var _searchPage: Int? = null

    init {
        loadSearchRecommend()
    }

     fun loadSearchRecommend() {
        _api.getSearchRecommend().enqueue(object : Callback<SearchRecommend> {
            override fun onResponse(
                call: Call<SearchRecommend>,
                response: Response<SearchRecommend>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK) {
                    val body = response.body()?.searchRecommendList
                    _searchRecommend.postValue(body)
                } else {
                    LogUtils.d(this@SearchViewModel, "code == ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SearchRecommend>, t: Throwable) {
                LogUtils.d(this@SearchViewModel, "Throwable ==> $t")
            }
        })
    }

    private fun loadSearchContent(page: Int, keyword: String) {
        _searchLoadState.value = NetLoadState.Loading
        val map = mapOf("page" to page.toString(),"keyword" to keyword)
        _api.getSearchContent(map)
            .enqueue(object :
            Callback<SearchPageContent> {
            override fun onResponse(
                call: Call<SearchPageContent>,
                response: Response<SearchPageContent>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK) {
                    val body =
                        response.body()?.SearchPageData?.searchPageDataResponse?.resultList?.searchPageDataList
                    _searchContent.postValue(body)
                    _searchLoadState.value = NetLoadState.Successful
                } else {
                    _searchLoadState.value = NetLoadState.Error
                }
            }

            override fun onFailure(call: Call<SearchPageContent>, t: Throwable) {
                _searchLoadState.value = NetLoadState.Error
            }
        })
    }

    fun addSearchHistory(searchText: String) {
        viewModelScope.launch {
            val list = getSearchHistory(searchText)
            if (list.isNotEmpty()){
                _searchHistoryRepository.removeSearchHistory(* list.toTypedArray())
            }
            _searchHistoryRepository.addSearchHistory(searchText)
        }
    }

    fun deleteSearchHistory() {
        _searchHistoryRepository.removeAllSearchHistory()
    }

    private suspend fun getSearchHistory(string: String): List<SearchHistory>{
        return _searchHistoryRepository.getSearchHistory(string)
    }

    fun reLoad(){
        if (_searchPage != null && !_searchText.isNullOrEmpty()){
            loadSearchContent(_searchPage!!, _searchText!!)
        }
    }

    fun load(page: Int, keyword: String){
        isLoadMore = false
        _searchText = keyword
        _searchPage = page
        loadSearchContent(page, keyword)
    }

    fun loadMore(){
        if (_searchPage != null && !_searchText.isNullOrEmpty()){
            isLoadMore = true
            loadSearchContent(_searchPage!!, _searchText!!)
        }
    }

}
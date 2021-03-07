package zzz.bing.ticketunion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
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
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.RetrofitManager
import java.net.HttpURLConnection

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create(Api::class.java)
    private val _searchHistoryRepository by lazy { SearchHistoryRepository() }

    private val _searchRecommend: MutableLiveData<List<SearchRecommendContent>> by lazy { MutableLiveData() }
    private val _searchContent: MutableLiveData<List<SearchData>> by lazy { MutableLiveData() }
    private val _searchHistory: LiveData<List<SearchHistory>> by lazy { _searchHistoryRepository.getAllSearchHistory() }

    val searchRecommend:LiveData<List<SearchRecommendContent>> get() = _searchRecommend
    val searchContent:LiveData<List<SearchData>> get() = _searchContent
    val searchHistory:LiveData<List<SearchHistory>> get() = _searchHistory

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
                    LogUtils.d(this@SearchViewModel, "searchRecommend ==> $body")
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

    fun loadSearchContent(page: Int, keyword: String) {
        _api.getSearchContent(page.toString(), keyword).enqueue(object :
            Callback<SearchPageContent> {
            override fun onResponse(
                call: Call<SearchPageContent>,
                response: Response<SearchPageContent>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK) {
                    val body =
                        response.body()?.SearchPageData?.searchPageDataResponse?.resultList?.searchPageDataList
                    LogUtils.d(this@SearchViewModel, "body ==> $body")
                    _searchContent.postValue(body)
                } else {
                    LogUtils.d(this@SearchViewModel, "code == ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SearchPageContent>, t: Throwable) {
                LogUtils.d(this@SearchViewModel, "Throwable ==> $t")
            }
        })
    }

    fun addSearchHistory(searchText: String) {
        viewModelScope.launch {
            if (isExist(searchText)){
                _searchHistoryRepository.addSearchHistory(searchText)
            }
        }
    }

    fun deleteSearchHistory() {
        _searchHistoryRepository.deleteAllSearchHistory()
    }

//    private fun getAllSearchHistory() {
//        searchHistory = _searchHistoryRepository.getAllSearchHistory()
//    }

    private suspend fun isExist(string: String): Boolean{
        val list = _searchHistoryRepository.getSearchHistory(string)
        return list.isNullOrEmpty()
    }

}
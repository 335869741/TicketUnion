package zzz.bing.ticketunion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val searchRecommend: MutableLiveData<List<SearchRecommendContent>> by lazy { MutableLiveData() }
    val searchContent: MutableLiveData<List<SearchData>> by lazy { MutableLiveData() }
    val searchHistory: LiveData<List<SearchHistory>> by lazy { _searchHistoryRepository.getAllSearchHistory() }

    fun loadSearchRecommend() {
        _api.getSearchRecommend().enqueue(object : Callback<SearchRecommend> {
            override fun onResponse(
                call: Call<SearchRecommend>,
                response: Response<SearchRecommend>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()?.code == Constant.RESPONSE_OK) {
                    val body = response.body()?.searchRecommendList
                    LogUtils.d(this@SearchViewModel, "searchRecommend ==> $body")
                    searchRecommend.postValue(body)
                } else {
                    LogUtils.d(this@SearchViewModel, "code == ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SearchRecommend>, t: Throwable) {
//                TODO("Not yet implemented")
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
                    searchContent.postValue(body)
                } else {
                    LogUtils.d(this@SearchViewModel, "code == ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SearchPageContent>, t: Throwable) {
//                TODO("Not yet implemented")
                LogUtils.d(this@SearchViewModel, "Throwable ==> $t")
            }
        })
    }

    fun addSearchHistory(searchText: String) {
        _searchHistoryRepository.addSearchHistory(searchText)
    }

    fun deleteSearchHistory() {
        _searchHistoryRepository.deleteAllSearchHistory()
    }

//    private fun getAllSearchHistory() {
//        searchHistory = _searchHistoryRepository.getAllSearchHistory()
//    }



}
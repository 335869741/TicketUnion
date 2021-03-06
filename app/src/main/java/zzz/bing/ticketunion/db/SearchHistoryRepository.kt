package zzz.bing.ticketunion.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.runBlocking
import zzz.bing.ticketunion.BaseApplication
import zzz.bing.ticketunion.db.database.SearchHistoryDatabase
import zzz.bing.ticketunion.db.entity.SearchHistory

class SearchHistoryRepository {
    private val _searchHistoryDao by lazy {
        SearchHistoryDatabase.getSearchHistoryDatabase(
            BaseApplication.getContext().applicationContext
        ).userSearchHistoryDao()
    }

    fun addSearchHistory(vararg searchHistory: SearchHistory) = runBlocking {
        _searchHistoryDao.insertSearchHistory(*searchHistory)
    }

    fun addSearchHistory(vararg searchText: String) = runBlocking {
        val searchHistoryList:ArrayList<SearchHistory> = ArrayList()
        searchText.forEach { string ->
            searchHistoryList.add(SearchHistory(0,string))
        }
        val searchHistoryArray = searchHistoryList.toTypedArray()
        _searchHistoryDao.insertSearchHistory(*searchHistoryArray)
    }

    fun deleteAllSearchHistory() = runBlocking{
        _searchHistoryDao.deleteAllSearchHistory()
    }

    fun getAllSearchHistory():LiveData<List<SearchHistory>>{
        return _searchHistoryDao.findAllSearchHistory()
    }

}
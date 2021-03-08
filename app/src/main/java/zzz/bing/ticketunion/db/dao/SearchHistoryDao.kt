package zzz.bing.ticketunion.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import zzz.bing.ticketunion.db.entity.SearchHistory

@Dao
interface SearchHistoryDao {

    @Insert
    suspend fun insertSearchHistory(vararg historyS: SearchHistory)

    @Query("DELETE FROM search_history")
    suspend fun deleteAllSearchHistory()

    @Query("SELECT * FROM search_history ORDER BY id DESC")
    fun findAllSearchHistory(): LiveData<List<SearchHistory>>

    @Query("SELECT * FROM search_history where search_text = :searchText")
    suspend fun findSearchHistoryBySearchText(searchText:String) :List<SearchHistory>

    @Delete
    suspend fun deleteSearchHistory(vararg searchHistory: SearchHistory)
}
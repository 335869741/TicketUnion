package zzz.bing.ticketunion.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import zzz.bing.ticketunion.db.entity.SearchHistory

@Dao
interface SearchHistoryDao {

    @Insert
    fun insertSearchHistory(vararg historyS: SearchHistory)

    @Query("DELETE FROM search_history")
    fun deleteAllSearchHistory()

    @Query("SELECT * FROM search_history")
    fun findAllSearchHistory(): LiveData<List<SearchHistory>>
}
package zzz.bing.ticketunion.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory @JvmOverloads constructor(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id:Int = 0,
    @ColumnInfo(name = "search_text")
    var searchText:String = "searchText"
)

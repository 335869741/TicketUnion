package zzz.bing.ticketunion.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory @JvmOverloads constructor(
    @PrimaryKey
    var id:Int = 0,
    var string:String = ""
)

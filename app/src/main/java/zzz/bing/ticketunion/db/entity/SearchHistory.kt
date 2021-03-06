package zzz.bing.ticketunion.db.entity

import androidx.room.Entity

@Entity(tableName = "search_history")
data class SearchHistory @JvmOverloads constructor(
    var id:Int = 0,
    var string:String = ""
)

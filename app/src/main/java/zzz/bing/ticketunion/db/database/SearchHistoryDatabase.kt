package zzz.bing.ticketunion.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zzz.bing.ticketunion.db.dao.SearchHistoryDao
import zzz.bing.ticketunion.db.entity.SearchHistory
import zzz.bing.ticketunion.utils.Constant

@Database(entities = [SearchHistory::class],version = Constant.DATABASE_VERSION, exportSchema = true)
abstract class SearchHistoryDatabase :RoomDatabase(){
    abstract fun userSearchHistoryDao(): SearchHistoryDao

    companion object{
        private var instance: SearchHistoryDatabase? = null

        @Synchronized
        fun getSearchHistoryDatabase(context: Context): SearchHistoryDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, SearchHistoryDatabase::class.java, "user_search_history_database.db")
                    .fallbackToDestructiveMigration()//版本迁移时不保留数据 DATABASE_VERSION变更
                    .build()
            }
            return instance!!
        }
    }
}
package com.astro.informme.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(News::class), version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsRoomDatabase? = null

        fun getDatabase(context: Context): NewsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
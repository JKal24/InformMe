package com.astro.informme.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_table")
    fun getNews() : Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news: News)

    @Query("DELETE FROM news_table")
    fun deleteAll()

    @Query("SELECT COUNT(id) FROM news_table")
    fun getNewsCount() : Int
}
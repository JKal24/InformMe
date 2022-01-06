package com.astro.informme.data

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val newsDao : NewsDao) {
    val allWords: Flow<List<News>> = newsDao.getNews()

    fun insert(news: News) = newsDao.insert(news)

     fun empty() = newsDao.deleteAll()

    fun getNews() = newsDao.getNews()
}
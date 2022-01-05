package com.astro.informme.data

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val newsDao : NewsDao) {
    val allWords: Flow<List<News>> = newsDao.getNews()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(news: News) {
        newsDao.insert(news)
    }

    @WorkerThread
    suspend fun count(): Int {
        return newsDao.getNewsCount()
    }

    @WorkerThread
    suspend fun empty() {
        newsDao.getNewsCount()
    }

    @WorkerThread
    suspend fun getNews(): Flow<List<News>> {
        return newsDao.getNews()
    }
}
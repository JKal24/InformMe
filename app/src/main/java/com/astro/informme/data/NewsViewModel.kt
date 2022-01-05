package com.astro.informme.data

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    val allWords: LiveData<List<News>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(news: News) = viewModelScope.launch {
        repository.insert(news)
    }

//    fun count(): LiveData<Int> {
//        var result = MutableLiveData<Int>()
//        viewModelScope.launch {
//            result.value = repository.count()
//        }
//        return result
//    }

    fun empty() = viewModelScope.launch {
        repository.empty()
    }

//    fun getNews(): LiveData<List<News>> {
//        var result = MutableLiveData<News>()
//        viewModelScope.launch {
//            result.postValue(repository.getNews().asLiveData(Dispatchers.Default))
//        }
//    }
}

class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
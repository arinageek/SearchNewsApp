package com.example.searchnewsapp.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val repository: NewsRepository,
    state: SavedStateHandle) : ViewModel() {

    init{
        getBreakingNews()
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_VALUE)
    lateinit var breakingNews: LiveData<PagingData<Article>>

    var searchNews = currentQuery.switchMap {
        repository.searchNews(it).cachedIn(viewModelScope)
    }

    fun getBreakingNews() {
        breakingNews = repository.getBreakingNews()
    }

    fun searchNews(query: String){
        currentQuery.value = query
    }

    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_VALUE = "cats"
    }
}
package com.example.searchnewsapp.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        breakingNews = repository.getBreakingNews().cachedIn(viewModelScope)
    }

    fun searchNews(query: String){
        currentQuery.value = query
    }

    fun upsertArticle(article: Article) = viewModelScope.launch {
        repository.upsertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch{
        repository.deleteArticle(article)
    }

    fun getAllArticles() = repository.getAllArticles()


    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_VALUE = "cats"
    }
}
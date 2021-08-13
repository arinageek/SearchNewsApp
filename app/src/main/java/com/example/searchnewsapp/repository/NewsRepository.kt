package com.example.searchnewsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.searchnewsapp.api.BreakingNewsPagingSource
import com.example.searchnewsapp.api.NewsApi
import com.example.searchnewsapp.api.SearchNewsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor (private val api: NewsApi) {

    fun getBreakingNews() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            BreakingNewsPagingSource(api)
        }
    ).liveData

    fun searchNews(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SearchNewsPagingSource(api, query)
        }
    ).liveData
}
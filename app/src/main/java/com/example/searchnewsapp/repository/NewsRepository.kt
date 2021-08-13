package com.example.searchnewsapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.api.BreakingNewsPagingSource
import com.example.searchnewsapp.api.NewsApi
import com.example.searchnewsapp.api.SearchNewsPagingSource
import com.example.searchnewsapp.db.NewsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor (
    private val api: NewsApi,
    private val dao: NewsDao
) {

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

    suspend fun upsertArticle(article: Article){
        dao.upsert(article)
    }

    suspend fun deleteArticle(article: Article){
        dao.delete(article)
    }

    fun getAllArticles() = dao.getAllArticles()

}
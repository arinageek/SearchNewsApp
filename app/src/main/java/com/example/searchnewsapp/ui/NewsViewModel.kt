package com.example.searchnewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (private val repository: NewsRepository) : ViewModel() {

    init{
        getBreakingNews()
    }

    lateinit var breakingNews: LiveData<PagingData<Article>>

    fun getBreakingNews() {
        breakingNews = repository.getBreakingNews()
    }
}
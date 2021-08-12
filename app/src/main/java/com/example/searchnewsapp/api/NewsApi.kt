package com.example.searchnewsapp.api

import com.example.mynewsapp.api.NewsResponse
import com.example.searchnewsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q")
        query: String,
        @Query("page")
        pageNumber: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}
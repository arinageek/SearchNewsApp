package com.example.searchnewsapp.api

import androidx.paging.PagingSource
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.util.Constants.Companion.API_KEY
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1

class SearchNewsPagingSource(
    private val api: NewsApi,
    private val query: String
): PagingSource<Int, Article>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 1
        return try{
            val response = api.getAllNews(query, position, API_KEY)
            val news = response.body()?.articles
            if(news != null){
                LoadResult.Page(
                    data = news,
                    prevKey = if(position == NEWS_STARTING_PAGE_INDEX) null else position-1,
                    nextKey = if(news.isEmpty()) null else position+1
                )
            }else{
                LoadResult.Error(IOException("News is null"))
            }

        }catch(exc: IOException){
            LoadResult.Error(exc)
        }catch(exc: HttpException){
            LoadResult.Error(exc)
        }
    }


}
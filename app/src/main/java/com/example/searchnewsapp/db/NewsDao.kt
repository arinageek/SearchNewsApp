package com.example.searchnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynewsapp.api.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>
}
package com.example.searchnewsapp.di

import android.content.Context
import androidx.room.Room
import com.example.searchnewsapp.api.NewsApi
import com.example.searchnewsapp.db.NewsDao
import com.example.searchnewsapp.db.NewsDatabase
import com.example.searchnewsapp.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext appContext: Context): NewsDatabase =
        Room.databaseBuilder(
            appContext,
            NewsDatabase::class.java,
            "news_database.db"
        ).build()

    @Provides
    @Singleton
    fun provideNewsDao(db: NewsDatabase): NewsDao = db.newsDao()

}
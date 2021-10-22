package com.example.newsapp_02.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp_02.data.db.ArticleDao
import com.example.newsapp_02.data.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDao(newsDatabase: NewsDatabase): ArticleDao {
        return newsDatabase.getArticleDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): NewsDatabase {
        return Room.databaseBuilder(app.applicationContext, NewsDatabase::class.java, "news_database.db")
            .build()
    }

}
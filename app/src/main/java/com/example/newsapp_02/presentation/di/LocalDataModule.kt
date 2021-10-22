package com.example.newsapp_02.presentation.di

import com.example.newsapp_02.data.db.ArticleDao
import com.example.newsapp_02.data.repository.dataSource.NewsLocalDataSource
import com.example.newsapp_02.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideNewsLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDao)
    }

}
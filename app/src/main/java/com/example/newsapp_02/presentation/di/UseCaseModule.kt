package com.example.newsapp_02.presentation.di

import com.example.newsapp_02.domain.repository.NewsRepository
import com.example.newsapp_02.domain.use_case.GetLatestNewsUseCase
import com.example.newsapp_02.domain.use_case.GetSavedNewsUseCase
import com.example.newsapp_02.domain.use_case.GetSearchedNewsUseCase
import com.example.newsapp_02.domain.use_case.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideLatestNewsUseCase(newsRepository: NewsRepository): GetLatestNewsUseCase {
        return GetLatestNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSearchNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }


}
package com.example.newsapp_02.presentation.di

import com.example.newsapp_02.domain.repository.NewsRepository
import com.example.newsapp_02.domain.use_case.GetLatestNewsUseCase
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

}
package com.example.newsapp_02.presentation.di

import android.app.Application
import com.example.newsapp_02.domain.use_case.UseCasesWrapper
import com.example.newsapp_02.presentation.view_model.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        useCasesWrapper: UseCasesWrapper
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            application,
            useCasesWrapper
        )
    }

}
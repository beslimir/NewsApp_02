package com.example.newsapp_02.domain.use_case

import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }

}
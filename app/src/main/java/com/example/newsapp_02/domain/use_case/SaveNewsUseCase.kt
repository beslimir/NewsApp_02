package com.example.newsapp_02.domain.use_case

import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)

}
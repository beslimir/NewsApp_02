package com.example.newsapp_02.domain.use_case

import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(article: Article) = newsRepository.deleteNews(article)

}
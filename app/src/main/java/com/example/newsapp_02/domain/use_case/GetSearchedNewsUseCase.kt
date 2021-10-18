package com.example.newsapp_02.domain.use_case

import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(searchQuery: String): Resource<APIResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }

}
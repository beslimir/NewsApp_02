package com.example.newsapp_02.data.repository.dataSourceImpl

import com.example.newsapp_02.data.api.NewsAPIService
import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
) : NewsRemoteDataSource {

    override suspend fun getLatestNews(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getLatestNews(country, page)
    }
}
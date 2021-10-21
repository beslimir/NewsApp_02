package com.example.newsapp_02.data.repository.dataSource

import com.example.newsapp_02.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getLatestNews(country: String, page: Int): Response<APIResponse>
    suspend fun getSearchNews(country: String, query: String): Response<APIResponse>

}
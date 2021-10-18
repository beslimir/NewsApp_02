package com.example.newsapp_02.data.repository.dataSource

import com.example.newsapp_02.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getLatestNews(): Response<APIResponse>

}
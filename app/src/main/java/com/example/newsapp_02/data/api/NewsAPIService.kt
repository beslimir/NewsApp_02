package com.example.newsapp_02.data.api

import com.example.newsapp_02.BuildConfig
import com.example.newsapp_02.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("api/1/news")
    suspend fun getLatestNews(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

}
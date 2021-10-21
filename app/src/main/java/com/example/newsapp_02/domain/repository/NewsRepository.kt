package com.example.newsapp_02.domain.repository

import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    //api communication
    suspend fun getLatestNews(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchNews(country: String, query: String): Resource<APIResponse>

    //local database
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>> //returns data stream, no need for suspend function
}
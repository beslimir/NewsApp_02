package com.example.newsapp_02.data.repository.dataSource

import com.example.newsapp_02.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun saveNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

}
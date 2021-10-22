package com.example.newsapp_02.data.repository.dataSource

import com.example.newsapp_02.data.model.Article

interface NewsLocalDataSource {

    suspend fun saveNews(article: Article)

}
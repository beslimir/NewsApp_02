package com.example.newsapp_02.data.repository.dataSourceImpl

import com.example.newsapp_02.data.db.ArticleDao
import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDao: ArticleDao
) : NewsLocalDataSource {

    override suspend fun saveNews(article: Article) {
        articleDao.upsert(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return articleDao.getSavedNews()
    }
}
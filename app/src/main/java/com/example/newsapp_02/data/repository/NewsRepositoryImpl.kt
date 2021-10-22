package com.example.newsapp_02.data.repository

import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.data.repository.dataSource.NewsLocalDataSource
import com.example.newsapp_02.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.domain.repository.NewsRepository
import com.example.newsapp_02.domain.use_case.DeleteSavedNewsUseCase
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }

        return Resource.Error(response.message())
    }

    override suspend fun getLatestNews(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getLatestNews(country, page))
    }

    override suspend fun getSearchNews(country: String, query: String): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchNews(country, query))
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveNews(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteNews(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedNews()
    }
}
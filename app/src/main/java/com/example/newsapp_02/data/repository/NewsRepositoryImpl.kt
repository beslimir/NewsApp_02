package com.example.newsapp_02.data.repository

import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource
): NewsRepository {

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

    override suspend fun getSearchedNews(searchQuery: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}
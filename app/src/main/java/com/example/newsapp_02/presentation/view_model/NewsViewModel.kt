package com.example.newsapp_02.presentation.view_model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.domain.use_case.UseCasesWrapper
import com.example.newsapp_02.presentation.NewsApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    val app: Application,
    private val useCasesWrapper: UseCasesWrapper
) : AndroidViewModel(app) {

    val latestNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getLatestNews(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        latestNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val apiResult = useCasesWrapper.getLatestNews(country, page)
                latestNews.postValue(apiResult)
            } else {
                latestNews.postValue(Resource.Error("Internet is not available!"))
            }
        } catch (e: Exception) {
            latestNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getSearchNews(country: String, query: String) = viewModelScope.launch(Dispatchers.IO) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val apiResult = useCasesWrapper.getSearchedNews(country, query)
                searchNews.postValue(apiResult)
            } else {
                searchNews.postValue(Resource.Error("No internet connection!"))
            }
        } catch (e: Exception) {
            searchNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun saveNews(article: Article) = viewModelScope.launch {
        useCasesWrapper.saveNews(article)
    }

    fun getSavedNews() = liveData {
        useCasesWrapper.getSavedNews.execute().collect { articlesList ->
            emit(articlesList)
        }
    }

    fun deleteNews(article: Article) = viewModelScope.launch {
        useCasesWrapper.deleteNews(article)
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }

        return false
    }


}
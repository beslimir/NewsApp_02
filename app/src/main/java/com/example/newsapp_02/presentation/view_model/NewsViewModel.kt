package com.example.newsapp_02.presentation.view_model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp_02.data.model.APIResponse
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.domain.use_case.GetLatestNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    val app: Application,
    val getLatestNewsUseCase: GetLatestNewsUseCase
): AndroidViewModel(app) {

    val latestNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getLatestNews(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        latestNews.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getLatestNewsUseCase.execute(country, page)
                latestNews.postValue(apiResult)
            } else {
                latestNews.postValue(Resource.Error("Internet is not available!"))
            }
        } catch (e: Exception) {
            latestNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null)
            return false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        }

        return false
    }


}
package com.example.newsapp_02.presentation.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp_02.domain.use_case.UseCasesWrapper

class NewsViewModelFactory(
    private val app: Application,
    private val useCasesWrapper: UseCasesWrapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            useCasesWrapper
        ) as T
    }
}
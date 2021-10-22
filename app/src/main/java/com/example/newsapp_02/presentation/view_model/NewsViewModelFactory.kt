package com.example.newsapp_02.presentation.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp_02.domain.use_case.GetLatestNewsUseCase
import com.example.newsapp_02.domain.use_case.GetSearchedNewsUseCase
import com.example.newsapp_02.domain.use_case.SaveNewsUseCase

class NewsViewModelFactory(
    private val app: Application,
    private val getLatestNewsUseCase: GetLatestNewsUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getLatestNewsUseCase, getSearchedNewsUseCase, saveNewsUseCase) as T
    }
}
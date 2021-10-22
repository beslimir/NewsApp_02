package com.example.newsapp_02.domain.use_case

data class UseCasesWrapper(
    val deleteNews: DeleteSavedNewsUseCase,
    val getLatestNews: GetLatestNewsUseCase,
    val getSavedNews: GetSavedNewsUseCase,
    val getSearchedNews: GetSearchedNewsUseCase,
    val saveNews: SaveNewsUseCase
)
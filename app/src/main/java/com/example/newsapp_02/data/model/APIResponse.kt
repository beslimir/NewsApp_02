package com.example.newsapp_02.data.model

data class APIResponse(
    val nextPage: Int,
    val results: List<Article>,
    val status: String,
    val totalResults: Int
)
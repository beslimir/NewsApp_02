package com.example.newsapp_02.data.db

import androidx.room.*
import com.example.newsapp_02.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Query("SELECT * from Article")
    fun getSavedNews(): Flow<List<Article>>

    @Delete
    suspend fun delete(article: Article)

}
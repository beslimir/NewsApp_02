package com.example.newsapp_02.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp_02.data.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(NewsTypeConverters::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}
package com.example.newsapp_02.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val content: Any?,
    val creator: Any?,
    val description: String?,
    val image_url: Any?,
    val keywords: Any?,
    val link: String,
    val pubDate: String?,
    val source_id: String?,
    val title: String?,
    val video_url: Any?
): Serializable
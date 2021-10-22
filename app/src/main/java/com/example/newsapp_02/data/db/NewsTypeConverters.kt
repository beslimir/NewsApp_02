package com.example.newsapp_02.data.db

import androidx.room.TypeConverter

class NewsTypeConverters {

    @TypeConverter
    fun fromAnyToString(any: Any?): String? {
        return any?.toString()
    }

    @TypeConverter
    fun fromStringToAny(string: String?): Any? {
        return string
    }

}
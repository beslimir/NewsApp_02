package com.example.newsapp_02.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsTypeConverters {

    @TypeConverter
    fun fromAnyToString(any: Any?): String? {
        return any?.toString()
    }

    @TypeConverter
    fun fromStringToAny(string: String?): Any? {
        val anyType = object: TypeToken<Any?>(){}.type
        return Gson().fromJson(string, anyType)
    }

}
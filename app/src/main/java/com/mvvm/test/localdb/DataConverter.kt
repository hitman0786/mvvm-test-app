package com.mvvm.test.localdb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mvvm.test.model.TopicData

class DataConverter {

    @TypeConverter
    fun fromCountryLangList(value: List<TopicData>): String {
        val gson = Gson()
        val type = object : TypeToken<List<TopicData>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<TopicData> {
        val gson = Gson()
        val type = object : TypeToken<List<TopicData>>() {}.type
        return gson.fromJson(value, type)
    }
}
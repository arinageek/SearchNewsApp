package com.example.searchnewsapp.util

import androidx.room.TypeConverter
import com.example.mynewsapp.api.Source


class Converters {
    @TypeConverter
    fun sourceToString(source: Source): String = source.name
    @TypeConverter
    fun stringToSource(str: String): Source = Source(str, str)
}
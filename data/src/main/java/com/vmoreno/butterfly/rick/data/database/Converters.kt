package com.vmoreno.butterfly.rick.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    private val listType = Types.newParameterizedType(List::class.java, String::class.java)
    private val listAdapter = Moshi.Builder().build().adapter<List<String>>(listType)

    @TypeConverter
    fun stringToList(string: String): List<String> {
        return listAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun listToString(members: List<String>): String {
        return listAdapter.toJson(members)
    }
}
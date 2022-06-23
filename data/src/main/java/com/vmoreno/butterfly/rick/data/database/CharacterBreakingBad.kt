package com.vmoreno.butterfly.rick.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class CharacterBreakingBad(
    @PrimaryKey(autoGenerate = false) val id: String,
    val charId: Int,
    val name: String,
    val birthday: String,
    @ColumnInfo(name = "occupation")
    @TypeConverters(Converters::class)
    val occupation: List<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val portrayed: String,
    val category: String,
    val favourite: Boolean
)
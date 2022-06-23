package com.vmoreno.butterfly.rick.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [CharacterBreakingBad::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun dbDao(): Dao
}
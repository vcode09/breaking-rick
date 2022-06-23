package com.vmoreno.butterfly.rick.data.database

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Query("SELECT * FROM CharacterBreakingBad")
    fun getAll(): List<CharacterBreakingBad>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: CharacterBreakingBad)

    @Query("DELETE FROM CharacterBreakingBad WHERE id = :id")
    fun deleteById(id: String)
}
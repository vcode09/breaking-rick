package com.vmoreno.butterfly.rick.data.datasource.source

import com.vmoreno.butterfly.rick.data.database.CharacterBreakingBad

interface LocalDataSource {
    suspend fun saveItem(item: CharacterBreakingBad)
    suspend fun deleteItemById(id: String)
    suspend fun getItems(): List<CharacterBreakingBad>
}
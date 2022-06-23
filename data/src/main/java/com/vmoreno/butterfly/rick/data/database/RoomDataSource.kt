package com.vmoreno.butterfly.rick.data.database

import com.vmoreno.butterfly.rick.data.datasource.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: Database) : LocalDataSource {

    private val dbDao = db.dbDao()

    override suspend fun saveItem(item: CharacterBreakingBad) {
        withContext(Dispatchers.IO) { dbDao.insertItem(item) }
    }

    override suspend fun deleteItemById(id: String) =
        withContext(Dispatchers.IO) { dbDao.deleteById(id) }

    override suspend fun getItems(): List<CharacterBreakingBad> =
        withContext(Dispatchers.IO) { dbDao.getAll() }

}
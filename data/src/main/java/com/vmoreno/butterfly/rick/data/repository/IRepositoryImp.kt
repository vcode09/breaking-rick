package com.vmoreno.butterfly.rick.data.repository

import com.vmoreno.butterfly.rick.data.database.RoomDataSource
import com.vmoreno.butterfly.rick.data.database.toDBItemEntity
import com.vmoreno.butterfly.rick.data.database.toDomainItemEntity
import com.vmoreno.butterfly.rick.data.datasource.source.RemoteDataSource
import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.entities.EpisodesBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.exceptions.ResultHandler
import com.vmoreno.butterfly.rick.domain.exceptions.resultHandlerOf
import com.vmoreno.butterfly.rick.usecases.repository.IRepository

class IRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localRepository: RoomDataSource
) : IRepository {

    override suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): ResultHandler<List<CharacterBreakingBadEntity>> =
        resultHandlerOf {
            remoteDataSource.api.charactersAsync(
                offset,
                limit
            ).map { it.mapToDomain() }
        }

    override suspend fun getEpisodes(
        episodeId: Int
    ): ResultHandler<List<EpisodesBreakingBadEntity>> =
        resultHandlerOf {
            remoteDataSource.api.episodeAsync(
                episodeId
            ).map { it.mapToDomain() }
        }

    override suspend fun getFavouriteItems(): List<CharacterBreakingBadEntity> {
        val listEntity = mutableListOf<CharacterBreakingBadEntity>()
        localRepository.getItems().map {
            listEntity.add(it.toDomainItemEntity())
        }
        return listEntity.toList()
    }

    override suspend fun deleteFavouriteItems(id: String) {
        localRepository.deleteItemById(id)
    }

    override suspend fun saveFavouriteItems(item: CharacterBreakingBadEntity) {
        localRepository.saveItem(item.toDBItemEntity())
    }
}
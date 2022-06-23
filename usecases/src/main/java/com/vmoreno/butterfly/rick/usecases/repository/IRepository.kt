package com.vmoreno.butterfly.rick.usecases.repository

import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.entities.EpisodesBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.exceptions.ResultHandler

interface IRepository {

    suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): ResultHandler<List<CharacterBreakingBadEntity>>

    suspend fun getEpisodes(
        episodeId: Int
    ): ResultHandler<List<EpisodesBreakingBadEntity>>

    suspend fun getFavouriteItems(): List<CharacterBreakingBadEntity>

    suspend fun deleteFavouriteItems(id: String)

    suspend fun saveFavouriteItems(item: CharacterBreakingBadEntity)

}
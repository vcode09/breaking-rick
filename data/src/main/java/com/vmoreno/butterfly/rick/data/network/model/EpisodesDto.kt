package com.vmoreno.butterfly.rick.data.network.model

import com.vmoreno.butterfly.rick.domain.entities.EpisodesBreakingBadEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodesDto(
    @field:Json(name = "episode_id")
    val episodeId: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "season")
    val season: String,
    @field:Json(name = "air_date")
    val airDate: String,
    @field:Json(name = "characters")
    val characters: List<String>,
    @field:Json(name = "episode")
    val episode: Int,
    @field:Json(name = "series")
    val series: String,
) {
    fun mapToDomain() = EpisodesBreakingBadEntity(
        episodeId,
        title,
        season,
        airDate,
        characters,
        episode,
        series
    )
}
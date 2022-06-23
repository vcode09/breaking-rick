package com.vmoreno.butterfly.rick.modules.character.entities

import com.vmoreno.butterfly.rick.domain.entities.EpisodesBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.utils.EMPTY_STRING
import com.vmoreno.butterfly.rick.domain.utils.SPACE_STRING
import com.vmoreno.butterfly.rick.utils.listStringToString
import java.io.Serializable

data class EpisodesBreakingBadUi(
    val episodeId: Int = -1,
    val title: String = EMPTY_STRING,
    val season: String = EMPTY_STRING,
    val airDate: String = EMPTY_STRING,
    val characters: String = EMPTY_STRING,
    val episode: Int = 0,
    val series: String = EMPTY_STRING
) : Serializable {
    companion object {
        fun mapFromDomain(item: EpisodesBreakingBadEntity) = EpisodesBreakingBadUi(
            item.episodeId,
            item.episode.toString() + SPACE_STRING + item.title + SPACE_STRING + item.airDate,
            item.season,
            item.airDate,
            item.characters.listStringToString(),
            item.episode,
            item.series
        )
    }
}
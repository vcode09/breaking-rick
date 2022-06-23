package com.vmoreno.butterfly.rick

import com.vmoreno.butterfly.rick.data.network.model.CharacterDto
import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity
import com.vmoreno.butterfly.rick.modules.character.entities.CharacterBreakingBadUi

val mockedEntity = CharacterBreakingBadEntity(
    1,
    "hank",
    "10-10-2010",
    listOf("any", "any"),
    "url",
    "rip",
    "Hank",
    listOf<Int>(1, 2),
    "tks",
    "ww",
    false
)

val mockedEmptyListBreakingBad = emptyList<CharacterBreakingBadEntity>()

val mockedBreakingBadUi = CharacterBreakingBadUi(
    1,
    "hank",
    "10-10-2010",
    "engineer",
    "url",
    "rip",
    "Hank",
    "1,2",
    "tks",
    "ww",
    false
)

val mockedDto = CharacterDto(
    1,
    "hank",
    "10-10-2010",
    listOf("any", "any"),
    "url",
    "rip",
    "Hank",
    listOf<Int>(1, 2),
    "tks",
    "ww"
)

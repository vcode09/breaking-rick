package com.vmoreno.butterfly.rick.data.database

import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity

fun CharacterBreakingBad.toDomainItemEntity(): CharacterBreakingBadEntity =
    CharacterBreakingBadEntity(
        charId = charId,
        name = name,
        birthday = birthday,
        occupation = occupation,
        img = img,
        status = status,
        nickname = nickname,
        appearance = emptyList(),
        portrayed = portrayed,
        category = category,
        isFavourite = favourite
    )

fun CharacterBreakingBadEntity.toDBItemEntity(): CharacterBreakingBad =
    CharacterBreakingBad(
        id = charId.toString(),
        charId = charId,
        name = name,
        birthday = birthday,
        occupation = occupation,
        img = img,
        status = status,
        nickname = nickname,
        portrayed = portrayed,
        category = category,
        favourite = isFavourite
    )
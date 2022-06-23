package com.vmoreno.butterfly.rick.domain.entities

data class CharacterBreakingBadEntity(
    val charId: Int,
    val name: String,
    val birthday: String,
    val occupation: List<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: List<Int>,
    val portrayed: String,
    val category: String,
    val isFavourite: Boolean = false
)
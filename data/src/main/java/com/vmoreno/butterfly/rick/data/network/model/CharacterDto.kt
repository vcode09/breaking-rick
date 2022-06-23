package com.vmoreno.butterfly.rick.data.network.model

import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDto(
    @field:Json(name = "char_id")
    val charId: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "birthday")
    val birthday: String,
    @field:Json(name = "occupation")
    val occupation: List<String>,
    @field:Json(name = "img")
    val img: String,
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "nickname")
    val nickname: String,
    @field:Json(name = "appearance")
    val appearance: List<Int>,
    @field:Json(name = "portrayed")
    val portrayed: String,
    @field:Json(name = "category")
    val category: String
) {
    fun mapToDomain() = CharacterBreakingBadEntity(
        charId,
        name,
        birthday,
        occupation,
        img,
        status,
        nickname,
        appearance,
        portrayed,
        category
    )
}
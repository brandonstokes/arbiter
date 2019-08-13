package io.fantasy.football.domain

data class Keeper(
    val id: Int,
    val name: String,
    val round: Int,
    val penalty: Int? = null
)

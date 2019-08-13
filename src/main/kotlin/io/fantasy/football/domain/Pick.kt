package io.fantasy.football.domain

data class Pick(
    val member: String,
    val round: Int,
    val position: Int,
    val pick: String? = null
)

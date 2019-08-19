package io.fantasy.football.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.fantasy.football.domain.deserializer.LeagueDeserializer

@JsonDeserialize(using = LeagueDeserializer::class)
data class League(
    val totalRounds: Int,
    val members: List<Member>
)

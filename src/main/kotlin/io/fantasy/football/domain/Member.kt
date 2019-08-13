package io.fantasy.football.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.fantasy.football.domain.deserializer.MemberDeserializer

@JsonDeserialize(using = MemberDeserializer::class)
data class Member(
    val name: String,
    val firstKeeper: Keeper? = null,
    val secondKeeper: Keeper? = null
)

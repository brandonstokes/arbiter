package io.fantasy.football.service.validation

import io.fantasy.football.domain.League

interface Validation {
    fun endorse(league: League): String?
}
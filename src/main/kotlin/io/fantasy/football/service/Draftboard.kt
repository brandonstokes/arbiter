package io.fantasy.football.service

import io.fantasy.football.domain.League
import io.fantasy.football.domain.Pick
import org.springframework.stereotype.Service

@Service
class Draftboard(private val round: Round) {

    fun arbitrate(league: League): List<Pick> {
        val default = draft(league)
        return listOf(default, compensatory(league, default.last().position)).flatten()
    }

    private fun draft(league: League): List<Pick> =
        round.picks(league, 0, false)

    private fun compensatory(league: League, lastPosition: Int): List<Pick> =
        round.picks(league, lastPosition, true)
}

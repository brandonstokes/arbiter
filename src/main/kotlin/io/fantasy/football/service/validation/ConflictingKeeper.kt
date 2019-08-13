package io.fantasy.football.service.validation

import io.fantasy.football.domain.League
import org.springframework.stereotype.Service

@Service
class ConflictingKeeper : Validation {
    override fun endorse(league: League): String? {
        val offenders = league.members
            .filter {
                it.firstKeeper != null &&
                    it.firstKeeper.round == it.secondKeeper?.round
            }

        return if (offenders.isNotEmpty()) "$offenders picked keepers in the same round!" else null
    }
}
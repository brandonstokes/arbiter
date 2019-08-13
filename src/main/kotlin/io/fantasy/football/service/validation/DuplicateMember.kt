package io.fantasy.football.service.validation

import io.fantasy.football.domain.League
import org.springframework.stereotype.Service

@Service
class DuplicateMember : Validation {
    override fun endorse(league: League): String? {
        val names = league.members.map { it.name }.groupingBy { it }.eachCount()
        val members = league.members.filter { names.getValue(it.name) > 1 }

        return if (members.isNotEmpty()) "$members have duplicate names!" else null
    }
}

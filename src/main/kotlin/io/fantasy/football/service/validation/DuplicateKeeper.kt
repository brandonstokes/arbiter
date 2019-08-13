package io.fantasy.football.service.validation

import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import org.springframework.stereotype.Service

@Service
class DuplicateKeeper : Validation {
    override fun endorse(league: League): String? {
        val offenders = league.members.filter { offenders(league, it) }

        return if (offenders.isNotEmpty()) "$offenders have keepers with the same id!" else null
    }

    private fun offenders(league: League, subject: Member): Boolean {
        val members = league.members.partition { it.name == subject.name }

        val self = members.first
            .any { it.firstKeeper != null && it.firstKeeper.id == it.secondKeeper?.id }

        val others = members.second
            .any { containsDuplicateKeeper(subject, it) }

        return self || others
    }

    private fun containsDuplicateKeeper(my: Member, your: Member): Boolean {
        if (my.firstKeeper == null)
            return false

        if (your.firstKeeper == null &&
            your.secondKeeper == null
        )
            return false

        val duplicatesFirstKeeper = {
            my.firstKeeper.id == your.firstKeeper?.id ||
                my.firstKeeper.id == your.secondKeeper?.id
        }

        val duplicatesSecondKeeper = {
            if (my.secondKeeper != null) {
                my.secondKeeper.id == your.firstKeeper?.id ||
                    my.secondKeeper.id == your.secondKeeper?.id
            } else false
        }

        return duplicatesFirstKeeper() || duplicatesSecondKeeper()
    }
}

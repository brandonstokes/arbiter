package io.fantasy.football.service

import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import io.fantasy.football.domain.Pick
import org.springframework.stereotype.Service

@Service
class Round {

    fun picks(league: League, lastPosition: Int, exclusions: Boolean): List<Pick> {
        var position = lastPosition + 1

        return IntRange(1, league.rounds)
            .map { round ->
                val adjusted = if (exclusions) 999 else round
                val pick = { members: List<Member> ->
                    members
                        .filter { exclusion(exclusions, it, round) }
                        .map {
                            val keeper = when (adjusted) {
                                it.firstKeeper?.round -> it.firstKeeper.name
                                it.secondKeeper?.round -> it.secondKeeper.name
                                else -> null
                            }

                            Pick(it.name, adjusted, position++, keeper)
                        }
                }

                if (round % 2 == 0) pick(league.members.reversed())
                else pick(league.members)
            }.flatten()
    }

    private fun exclusion(exclusions: Boolean, member: Member, round: Int): Boolean =
        if (exclusions) exclude(member, round)
        else !exclude(member, round)

    private fun exclude(member: Member, round: Int): Boolean =
        listOf(member.firstKeeper?.penalty, member.secondKeeper?.penalty).contains(round)
}

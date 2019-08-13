package io.fantasy.football.service.validation

import io.fantasy.football.domain.Keeper
import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class DuplicateKeeperTest {

    @Test
    fun `returns error message when two members have a keeper with the same id`() {
        val offenders = listOf(
            Member("member 1", firstKeeper = Keeper(1, "player", 1)),
            Member("member 2", firstKeeper = Keeper(1, "player", 2))
        )
        val members = listOf(offenders[0], offenders[1], Member("member 3"))

        assertEquals(
            "$offenders have keepers with the same id!",
            DuplicateKeeper().endorse(League(1, members))
        )
    }

    @Test
    fun `returns error message when member has two keepers with the same id`() {
        val offenders = listOf(
            Member(
                "member 1",
                firstKeeper = Keeper(1, "player", 1),
                secondKeeper = Keeper(1, "player", 2)
            )
        )
        val members = listOf(offenders[0], Member("member 3"))

        assertEquals(
            "$offenders have keepers with the same id!",
            DuplicateKeeper().endorse(League(1, members))
        )
    }

    @Test
    fun `null when all members have keepers with unique ids`() {
        assertNull(
            DuplicateKeeper()
                .endorse(
                    League(
                        1,
                        listOf(
                            Member("member 1", firstKeeper = Keeper(1, "player", 1)),
                            Member("member 2", firstKeeper = Keeper(2, "player", 2))
                        )
                    )
                )
        )
    }
}

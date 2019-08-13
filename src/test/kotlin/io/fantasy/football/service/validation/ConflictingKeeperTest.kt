package io.fantasy.football.service.validation

import io.fantasy.football.domain.Keeper
import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class ConflictingKeeperTest {

    @Test
    fun `returns error message when member has two keepers that are picked in the same round`() {
        val offenders = listOf(
            Member(
                "member 1",
                firstKeeper = Keeper(1, "player", 1),
                secondKeeper = Keeper(2, "player", 1)
            ),
            Member(
                "member 2",
                firstKeeper = Keeper(3, "player", 4),
                secondKeeper = Keeper(4, "player", 4)
            )
        )
        val members = listOf(offenders[0], offenders[1], Member("member 3"))

        assertEquals(
            "$offenders picked keepers in the same round!",
            ConflictingKeeper().endorse(League(1, members))
        )
    }

    @Test
    fun `null when member has keepers picked in different rounds`() {
        assertNull(
            ConflictingKeeper()
                .endorse(
                    League(
                        1,
                        listOf(
                            Member(
                                "",
                                firstKeeper = Keeper(1, "", 1),
                                secondKeeper = Keeper(2, "", 2)
                            )
                        )
                    )
                )
        )
    }
}

package io.fantasy.football.service

import io.fantasy.football.domain.Keeper
import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import io.fantasy.football.domain.Pick
import junit.framework.TestCase.assertEquals
import org.junit.Test

class RoundTest {

    @Test
    fun `returns list of members who are not penalized`() {
        val members = listOf(
            Member("a"),
            Member("b", firstKeeper = Keeper(1, "", 1, 1)),
            Member("c"),
            Member("d")
        )
        val actual = Round().picks(League(3, members), 0, false)

        assertEquals(
            listOf(
                Pick("a", 1, 1),
                Pick("c", 1, 2),
                Pick("d", 1, 3),
                Pick("d", 2, 4),
                Pick("c", 2, 5),
                Pick("b", 2, 6),
                Pick("a", 2, 7),
                Pick("a", 3, 8),
                Pick("b", 3, 9),
                Pick("c", 3, 10),
                Pick("d", 3, 11)
            ),
            actual
        )
    }

    @Test
    fun `returns list of penalized members in order of when they were penalized`() {
        val members = listOf(
            Member("a"),
            Member("b", firstKeeper = Keeper(1, "", 1, 1)),
            Member("c"),
            Member("d", firstKeeper = Keeper(1, "", 1, 2))
        )
        val actual = Round().picks(League(3, members), 10, true)

        assertEquals(
            listOf(
                Pick("b", 999, 11),
                Pick("d", 999, 12)
            ),
            actual
        )
    }
}

package io.fantasy.football.service

import io.fantasy.football.domain.Keeper
import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import io.fantasy.football.domain.Pick
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DraftboardTest {

    @Test
    fun `builds draftboard with no keepers`() {
        val league = League(
            3,
            listOf(
                Member("a"),
                Member("b"),
                Member("c"),
                Member("d"),
                Member("e"),
                Member("f")
            )
        )

        assertEquals(
            listOf(
                Pick("a", 1, 1),
                Pick("b", 1, 2),
                Pick("c", 1, 3),
                Pick("d", 1, 4),
                Pick("e", 1, 5),
                Pick("f", 1, 6),
                Pick("f", 2, 7),
                Pick("e", 2, 8),
                Pick("d", 2, 9),
                Pick("c", 2, 10),
                Pick("b", 2, 11),
                Pick("a", 2, 12),
                Pick("a", 3, 13),
                Pick("b", 3, 14),
                Pick("c", 3, 15),
                Pick("d", 3, 16),
                Pick("e", 3, 17),
                Pick("f", 3, 18)
            ),
            Draftboard(Round()).arbitrate(league)
        )
    }

    @Test
    fun `builds draftboard with one keeper and no penalty`() {
        val league = League(
            3,
            listOf(
                Member("a", firstKeeper = Keeper(1, "xx", 1)),
                Member("b"),
                Member("c"),
                Member("d"),
                Member("e"),
                Member("f")
            )
        )

        assertEquals(
            listOf(
                Pick("a", 1, 1, "xx"),
                Pick("b", 1, 2),
                Pick("c", 1, 3),
                Pick("d", 1, 4),
                Pick("e", 1, 5),
                Pick("f", 1, 6),
                Pick("f", 2, 7),
                Pick("e", 2, 8),
                Pick("d", 2, 9),
                Pick("c", 2, 10),
                Pick("b", 2, 11),
                Pick("a", 2, 12),
                Pick("a", 3, 13),
                Pick("b", 3, 14),
                Pick("c", 3, 15),
                Pick("d", 3, 16),
                Pick("e", 3, 17),
                Pick("f", 3, 18)
            ),
            Draftboard(Round()).arbitrate(league)
        )
    }

    @Test
    fun `builds draftboard with one keeper and one penalty, and adds a compensatory pick`() {
        val league = League(
            3,
            listOf(
                Member("a", firstKeeper = Keeper(1, "xx", 1, 2)),
                Member("b"),
                Member("c"),
                Member("d"),
                Member("e"),
                Member("f")
            )
        )

        assertEquals(
            listOf(
                Pick("a", 1, 1, "xx"),
                Pick("b", 1, 2),
                Pick("c", 1, 3),
                Pick("d", 1, 4),
                Pick("e", 1, 5),
                Pick("f", 1, 6),
                Pick("f", 2, 7),
                Pick("e", 2, 8),
                Pick("d", 2, 9),
                Pick("c", 2, 10),
                Pick("b", 2, 11),
                Pick("a", 3, 12),
                Pick("b", 3, 13),
                Pick("c", 3, 14),
                Pick("d", 3, 15),
                Pick("e", 3, 16),
                Pick("f", 3, 17),
                Pick("a", 999, 18)
            ),
            Draftboard(Round()).arbitrate(league)
        )
    }
}

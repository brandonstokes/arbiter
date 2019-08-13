package io.fantasy.football.service.validation

import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class DuplicateMemberTest {

    @Test
    fun `returns error message when two members have the same name`() {
        val offenders = listOf(Member("member"), Member("member"))
        assertEquals(
            "$offenders have duplicate names!",
            DuplicateMember().endorse(League(1, offenders))
        )
    }

    @Test
    fun `null when all members have unique names`() =
        assertNull(
            DuplicateMember().endorse(
                League(1, listOf(Member("member 1"), Member("member 2")))
            )
        )
}

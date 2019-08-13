package io.fantasy.football.domain.deserializer

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.fantasy.football.domain.Keeper
import io.fantasy.football.domain.Member
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class MemberDeserializerTest {

    private lateinit var mapper: ObjectMapper

    @Before
    fun setUp() {
        mapper = ObjectMapper().registerKotlinModule()
    }

    @Test
    fun `deserialize member`() {
        assertEquals(
            Member("a", Keeper(1, "a", 1)),
            mapper.readValue(
                from("valid-member.json"),
                Member::class.java
            )
        )
    }

    @Test
    fun `deserialize and reset second keeper round when its round equals first keeper penalty`() {
        assertEquals(
            Member(
                "a",
                Keeper(1, "aa", 1, 3),
                Keeper(2, "bb", 2, 5)
            ),
            mapper.readValue(
                from("second-keeper-round-conflicts-with-first-keeper-penalty.json"),
                Member::class.java
            )
        )
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when Member is null`() {
        mapper.readValue("", Member::class.java)
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when Member does not have name`() {
        mapper.readValue(
            from("member-null-name.json"),
            Member::class.java
        )
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when first keeper is null and second keeper exists`() {
        mapper.readValue(
            from("first-keeper-null-when-second-keeper-exists.json"),
            Member::class.java
        )
    }

    private fun from(resource: String): String =
        this::class.java.getResource("/fixtures/domain/member/$resource").readText()
}

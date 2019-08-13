package io.fantasy.football.domain.deserializer

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.fantasy.football.domain.League
import io.fantasy.football.domain.Member
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class LeagueDeserializerTest {

    private lateinit var mapper: ObjectMapper

    @Before
    fun setUp() {
        mapper = ObjectMapper().registerKotlinModule()
    }

    @Test
    fun `deserialize league`() {
        assertEquals(
            League(1, listOf(Member("a"))),
            mapper.readValue(
                from("valid-league.json"),
                League::class.java
            )
        )
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when league is empty string`() {
        mapper.readValue("", League::class.java)
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when rounds is null`() {
        mapper.readValue(from("null-rounds.json"), League::class.java)
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when members is null`() {
        mapper.readValue(from("null-members.json"), League::class.java)
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when two members have the same name`() {
        mapper.readValue(from("duplicate-members.json"), League::class.java)
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when the same keeper is kept more than once`() {
        mapper.readValue(from("duplicate-keepers.json"), League::class.java)
    }

    @Test(expected = JsonMappingException::class)
    fun `throws JsonMappingException when two keepers are picked in the same round`() {
        mapper.readValue(from("conflicting-keepers.json"), League::class.java)
    }

    private fun from(resource: String): String =
        this::class.java.getResource("/fixtures/domain/league/$resource").readText()
}

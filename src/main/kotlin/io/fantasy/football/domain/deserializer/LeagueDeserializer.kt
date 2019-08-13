package io.fantasy.football.domain.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.fantasy.football.domain.League
import io.fantasy.football.service.validation.ConflictingKeeper
import io.fantasy.football.service.validation.DuplicateKeeper
import io.fantasy.football.service.validation.DuplicateMember

class LeagueDeserializer : JsonDeserializer<League>() {

    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): League {
        val nodes = nullCheck(p)
        val league = League(nodes.first.asInt(), mapper.readValue(nodes.second.toString()))

        val validators = listOf(DuplicateMember(), DuplicateKeeper(), ConflictingKeeper())
        val errors = validators.mapNotNull { it.endorse(league) }

        if (errors.isNotEmpty()) throw JsonMappingException(p, "$errors")
        else return league
    }

    private fun nullCheck(p: JsonParser?): Pair<JsonNode, JsonNode> {
        val node = p?.codec?.readTree<JsonNode>(p)
            ?: throw JsonMappingException(p, "League can not be null!")

        val roundsNode = node.get("rounds")
        if (roundsNode.isNull) throw JsonMappingException(p, "rounds can not be null!")

        val membersNode = node.get("members")
        if (membersNode.isNull) throw JsonMappingException(p, "members can not be null!")

        return Pair(roundsNode, membersNode)
    }
}

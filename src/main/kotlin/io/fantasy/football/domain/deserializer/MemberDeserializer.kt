package io.fantasy.football.domain.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.fantasy.football.domain.Keeper
import io.fantasy.football.domain.Member
import org.springframework.boot.jackson.JsonComponent

@JsonComponent
class MemberDeserializer : JsonDeserializer<Member>() {

    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Member {
        val node = p?.codec?.readTree<JsonNode>(p) ?: throw JsonMappingException(p, "Member can not be null!")

        val nameNode = node.get("name")
        if (nameNode.isNull) throw JsonMappingException(p, "Member name can not be null!")

        val name = nameNode.asText()
        node.get("firstKeeper")?.let { fk ->
            val first = mapper.readValue(fk.toString(), Keeper::class.java)

            node.get("secondKeeper")?.let { sk ->
                val second = mapper.readValue(sk.toString(), Keeper::class.java)

                return if (first == null && second != null)
                    throw JsonMappingException(p, "First keeper cannot be null if second keeper is set!")
                else if (second != null && first.penalty == second.round)
                    Member(name, first, second.copy(round = first.penalty.minus(1)))
                else
                    Member(name, first, second)
            }

            return Member(name, first)
        }

        return Member(name)
    }
}

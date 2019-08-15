package io.fantasy.football

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class ArbiterControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `returns a static list with 200 of the best fantasy players`() {
        mvc.perform(get("/draft/players"))
            .andExpect(status().isOk)
            .andExpect(content().json(this::class.java.getResource("/players.json").readText(), true))
    }

    @Test
    fun `arbitrates 12 member league by penalizing members that have keepers with penalties`() {
        val request = this::class.java
            .getResource("/fixtures/request/twelve-members-thirteen-round-league.json")
            .readText()

        val expected = this::class.java
            .getResource("/fixtures/response/twelve-members-thirteen-round-league.json")
            .readText()

        mvc.perform(
            post("/draft/arbitrate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(expected, true))
    }
}

package io.fantasy.football

import io.fantasy.football.domain.League
import io.fantasy.football.domain.Pick
import io.fantasy.football.service.Draftboard
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/draft")
class ArbiterController(private val draftboard: Draftboard) {

    @GetMapping(
        "/players",
        produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun players(): ResponseEntity<String> =
        ResponseEntity.ok(this::class.java.getResource("/players.json").readText())

    @PostMapping(
        "/arbitrate",
        consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
        produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun arbitrate(@RequestBody league: League): ResponseEntity<List<Pick>> =
        ResponseEntity.ok(draftboard.arbitrate(league))
}

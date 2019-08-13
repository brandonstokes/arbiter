package io.fantasy.football

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArbiterApplication

fun main(args: Array<String>) {
    runApplication<ArbiterApplication>(*args)
}

package com.github.ozsie.webfluxtest

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
class WebFluxTestApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<WebFluxTestApplication>(*args)
}

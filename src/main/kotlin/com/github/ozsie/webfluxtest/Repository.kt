package com.github.ozsie.webfluxtest

import com.github.ozsie.webfluxtest.model.Item
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

internal fun init(repository: ItemRepository) = CommandLineRunner {
    val productFlux = Flux.just(
            Item(null, "A"),
            Item(null, "B"),
            Item(null, "C"))
            .flatMap { repository.save(it) }

    productFlux
            .thenMany(repository.findAll())
            .subscribe { println(it) }
}

interface ItemRepository : ReactiveMongoRepository<Item, String>

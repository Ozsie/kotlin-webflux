package com.github.ozsie.webfluxtest

import com.github.ozsie.webfluxtest.model.Item
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ItemRepository : ReactiveMongoRepository<Item, String>

internal fun init(repository: ItemRepository) = CommandLineRunner {
    Flux.just(
            Item(null, "A"),
            Item(null, "B"),
            Item(null, "C"))
            .flatMap { repository.save(it) }
            .thenMany(repository.findAll())
            .subscribe { println(it) }
}

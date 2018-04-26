package com.github.ozsie.webfluxtest

import com.github.ozsie.webfluxtest.configuration.HZ_INSTANCE
import com.github.ozsie.webfluxtest.configuration.HZ_MAP_NAME
import com.github.ozsie.webfluxtest.model.Item
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.IMap
import org.springframework.boot.CommandLineRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

//interface ItemRepository : ReactiveMongoRepository<Item, String>

internal fun init() = CommandLineRunner {

    val itemRepository: IMap<String, Item> = Hazelcast.getHazelcastInstanceByName(HZ_INSTANCE)
            .getMap<String, Item>(HZ_MAP_NAME)
    Flux.just(
            Item(value = "A"),
            Item(value = "B"),
            Item(value = "C"))
            .flatMap { item ->
                it.toMono().also {
                    itemRepository[item.id] = item
                }
            }
            .thenMany(itemRepository.values.toFlux())
            .subscribe { println(it) }
}

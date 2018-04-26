package com.github.ozsie.webfluxtest.handlers

import com.github.ozsie.webfluxtest.configuration.HZ_INSTANCE
import com.github.ozsie.webfluxtest.configuration.HZ_MAP_NAME
import com.github.ozsie.webfluxtest.model.Item
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.IMap
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

class ItemHandler {

    private val itemRepository: IMap<String, Item> = Hazelcast.getHazelcastInstanceByName(HZ_INSTANCE)
            .getMap<String, Item>(HZ_MAP_NAME)

    @Suppress("UNUSED_PARAMETER")
    fun getAllItems(request: ServerRequest) = ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(itemRepository.values.toFlux(), Item::class.java)

    fun getItem(request: ServerRequest) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(get(request.pathVariable("id")), Item::class.java)
            .switchIfEmpty(ServerResponse.notFound().build())

    fun updateItem(request: ServerRequest) = request
            .bodyToMono(Item::class.java)
            .zipWith(get(request.pathVariable("id")), { item, existingItem ->
                Item(existingItem.id, item.value)
            })
            .flatMap(::saveAndRespond)
            .switchIfEmpty(ServerResponse.notFound().build())

    fun addItem(request: ServerRequest) = request
            .bodyToMono(Item::class.java)
            .flatMap(::saveAndRespond)

    private fun saveAndRespond(item: Item) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(item.toMono().also { itemRepository.set(item.id, item) }, Item::class.java)

    private fun get(id: String) = itemRepository[id]?.toMono() ?: Mono.empty<Item>()
}



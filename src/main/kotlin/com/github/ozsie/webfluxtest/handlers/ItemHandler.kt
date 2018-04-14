package com.github.ozsie.webfluxtest.handlers

import com.github.ozsie.webfluxtest.ItemRepository
import com.github.ozsie.webfluxtest.model.Item
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class ItemHandler(private val itemRepository: ItemRepository) {

    @Suppress("UNUSED_PARAMETER")
    fun getAllItems(request: ServerRequest) = ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(itemRepository.findAll(), Item::class.java)

    fun getItem(request: ServerRequest) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(itemRepository.findById(request.pathVariable("id")), Item::class.java)

    fun updateItem(request: ServerRequest) = request.bodyToMono(Item::class.java)
                .zipWith(this.itemRepository.findById(request.pathVariable("id")), { item, existingItem ->
                    Item(existingItem.id, item.value)
                }).flatMap(::saveAndRespond).switchIfEmpty(ServerResponse.notFound().build())

    fun addItem(request: ServerRequest) = request.bodyToMono(Item::class.java).flatMap(::saveAndRespond)

    private fun saveAndRespond(item: Item) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(itemRepository.save(item), Item::class.java)
}

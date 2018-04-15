package com.github.ozsie.webfluxtest.routers

import com.github.ozsie.webfluxtest.handlers.ItemHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

fun router(itemHandler: ItemHandler) = router {
    accept(APPLICATION_JSON).nest {
        "/api".nest {
            "/items".nest {
                GET("/", itemHandler::getAllItems)
            }
            "/item".nest {
                PUT("/{id}", itemHandler::updateItem)
                GET("/{id}", itemHandler::getItem)
                POST("/add", itemHandler::addItem)
            }
        }
    }
}
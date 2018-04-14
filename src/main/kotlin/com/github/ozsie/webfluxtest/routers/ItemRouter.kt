package com.github.ozsie.webfluxtest.routers

import com.github.ozsie.webfluxtest.handlers.ItemHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

class ItemRouter(private val itemHandler: ItemHandler) {
    fun router() = router {
        "/api".nest {
            "/items".nest {
                accept(APPLICATION_JSON).nest {
                    GET("/", itemHandler::getAllItems)
                }
            }
            "/item".nest {
                accept(APPLICATION_JSON).nest {
                    PUT("/{id}", itemHandler::updateItem)
                    GET("/{id}", itemHandler::getItem)
                    POST("/add", itemHandler::addItem)
                }
            }
        }
    }
}

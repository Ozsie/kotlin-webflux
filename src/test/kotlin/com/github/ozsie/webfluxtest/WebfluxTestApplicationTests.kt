package com.github.ozsie.webfluxtest

import com.github.ozsie.webfluxtest.model.Item
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction

@ExtendWith(SpringExtension::class)
@SpringBootTest
class WebfluxTestApplicationTests {

	private lateinit var client: WebTestClient

	private var expectedList: MutableList<Item> = mutableListOf()

	@Autowired
	private lateinit var repository: ItemRepository

	@Autowired
	private lateinit var routes: RouterFunction<*>

	@BeforeEach
	fun before() {
        init(repository)
		client = WebTestClient
				.bindToRouterFunction(routes)
				.configureClient()
				.baseUrl("/api")
				.build()
        repository.findAll().collectList().block() ?: mutableListOf()
	}

	@Test
	fun testGetItems() {
        expectedList = repository.findAll().collectList().block() ?: mutableListOf()
		client
                .get()
				.uri("/items")
				.exchange()
				.expectStatus()
				.isOk
				.expectBodyList(Item::class.java)
                .isEqualTo<WebTestClient.ListBodySpec<Item>>(expectedList)
	}

    @Test
    fun testGetItem() {
        expectedList = repository.findAll().collectList().block() ?: mutableListOf()
        val item = expectedList[0]
        val x = client
                .get()
                .uri("/item/${item.id}")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody(Item::class.java)
                .returnResult()

        Assertions.assertEquals(item, x.responseBody)
    }

}

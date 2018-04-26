package com.github.ozsie.webfluxtest.model

//import org.springframework.data.annotation.Id
import java.io.Serializable
import java.util.*

data class Item(val id: String = UUID.randomUUID().toString(), val value: String) : Serializable

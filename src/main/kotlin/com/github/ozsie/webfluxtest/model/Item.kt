package com.github.ozsie.webfluxtest.model

import org.springframework.data.annotation.Id

data class Item(@Id val id: String?, val value: String)

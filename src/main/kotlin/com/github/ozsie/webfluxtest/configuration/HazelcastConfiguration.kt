package com.github.ozsie.webfluxtest.configuration

import com.hazelcast.config.Config
import com.hazelcast.config.MapConfig

const val HZ_INSTANCE = "hazelcast-instance"
const val HZ_MAP_NAME = "items"

fun hazelcastConfiguration() = Config().apply {
    instanceName = HZ_INSTANCE
    properties["hazelcast.phone.home.enabled"] = "false"
    addMapConfig(MapConfig().apply {
        name = HZ_MAP_NAME
    })
}
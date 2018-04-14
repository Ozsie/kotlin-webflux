package com.github.ozsie.webfluxtest

import com.github.ozsie.webfluxtest.handlers.ItemHandler
import com.github.ozsie.webfluxtest.routers.ItemRouter
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

// See application.properties context.initializer.classes entry
class Beans : ApplicationContextInitializer<GenericApplicationContext> {
    private fun initBeans() = beans {
        bean {
            ItemHandler(ref())
        }
        bean {
            ItemRouter(ref()).router()
        }
        bean {
            init(ref())
        }
    }

    override fun initialize(context: GenericApplicationContext) = initBeans().initialize(context)
}

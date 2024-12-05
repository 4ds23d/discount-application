package com.ala.shop.config

import com.ala.shop.core.Amount
import com.ala.shop.core.AmountJacksonDeserializer
import com.ala.shop.core.AmountJacksonSerializer
import com.ala.shop.core.ProductId
import com.ala.shop.core.ProductIdJacksonDeserializer
import com.ala.shop.core.ProductIdJacksonSerializer
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapperModule(): Module {
        val module = SimpleModule()
        module.addSerializer(ProductId::class.java, ProductIdJacksonSerializer())
        module.addDeserializer(ProductId::class.java, ProductIdJacksonDeserializer())
        module.addSerializer(Amount::class.java, AmountJacksonSerializer())
        module.addDeserializer(Amount::class.java, AmountJacksonDeserializer())
        return module
    }
}
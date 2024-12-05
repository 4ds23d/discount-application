package com.ala.shop.discount

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class DiscountSerializer {
    private val mapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()

    fun serialize(discount: Discount): String {
        return mapper.writeValueAsString(discount)
    }

    fun deserialize(data: String): Discount {
        return mapper.readValue(data, Discount::class.java)
    }
}
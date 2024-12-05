package com.ala.shop.core

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.core.convert.converter.Converter

class ProductIdJacksonSerializer: JsonSerializer<ProductId>() {
    override fun serialize(p0: ProductId?, gen: JsonGenerator?, p2: SerializerProvider?) {
        gen?.writeString(p0.toString())
    }
}

class ProductIdJacksonDeserializer: JsonDeserializer<ProductId>() {
    override fun deserialize(parser: JsonParser?, p1: DeserializationContext?): ProductId {
        parser?.text.let {
            return ProductId.from(it!!)
        }
    }
}

class StringToProductIdConverter : Converter<String, ProductId> {
    override fun convert(source: String): ProductId {
        return ProductId.from(source)
    }
}
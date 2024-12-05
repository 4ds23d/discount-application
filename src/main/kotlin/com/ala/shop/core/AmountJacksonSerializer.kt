package com.ala.shop.core

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.core.convert.converter.Converter

class AmountJacksonSerializer: JsonSerializer<Amount>() {
    override fun serialize(p0: Amount?, gen: JsonGenerator?, p2: SerializerProvider?) {
        gen?.writeString(p0?.value.toString())
    }
}

class AmountJacksonDeserializer: JsonDeserializer<Amount>() {
    override fun deserialize(parser: JsonParser?, p1: DeserializationContext?): Amount {
        parser?.text.let {
            val restoreValue = it?.toInt() ?: throw IllegalArgumentException("Cannot convert [$it] to Int")
            return Amount(restoreValue)
        }
    }
}

class StringToAmountConverter : Converter<String, Amount> {
    override fun convert(source: String): Amount {
        return Amount(source.toInt())
    }
}
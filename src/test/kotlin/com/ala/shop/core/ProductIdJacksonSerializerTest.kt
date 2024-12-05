package com.ala.shop.core

import com.ala.shop.config.ObjectMapperConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductIdJacksonSerializerTest {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModules(ObjectMapperConfiguration().objectMapperModule())

    @Test
    fun `serialize and deserialize object`() {
        // given
        val productId = ProductId.newId()

        // when
        val json = objectMapper.writeValueAsString(productId)
        val restored = objectMapper.readValue(json, ProductId::class.java)

        // then
        assertThat(restored).isEqualTo(productId)
    }

    @Test
    fun `properly serialize null value`() {
        // when
        val json = objectMapper.writeValueAsString(Data(null))
        val restored = objectMapper.readValue(json, Data::class.java)

        // then
        assertThat(restored).isNotNull()
        assertThat(restored.productId).isNull()
    }

    data class Data(val productId: ProductId?)

}
package com.ala.shop.config

import com.ala.shop.core.Amount
import com.ala.shop.core.ProductId
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class ObjectMapperConfigurationTest {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModules(ObjectMapperConfiguration().objectMapperModule())

    @Nested
    inner class ProductIdSerializer {
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
            val json = objectMapper.writeValueAsString(ProductData(null))
            val restored = objectMapper.readValue(json, ProductData::class.java)

            // then
            assertThat(restored).isNotNull()
            assertThat(restored.productId).isNull()
        }

        @Test
        fun `from json`() {
            // given
            val json = "{\"productId\": \"72f1fedb-adf7-4db1-9c8d-7e9415d47e5d\"}"

            // when
            val restored = objectMapper.readValue(json, ProductData::class.java)

            // then
            assertThat(restored).isEqualTo(ProductData(ProductId.from("72f1fedb-adf7-4db1-9c8d-7e9415d47e5d")))
        }
    }

    @Nested
    inner class AmountSerializer {

        @Test
        fun `serialize and deserialize object`() {
            // given
            val amount = Amount(1)

            // when
            val json = objectMapper.writeValueAsString(amount)
            val restored = objectMapper.readValue(json, Amount::class.java)

            // then
            assertThat(restored).isEqualTo(amount)
        }

        @Test
        fun `from json`() {
            // given
            val json = "{\"amount\": 1}"

            // when
            val restored = objectMapper.readValue(json, AmountData::class.java)

            // then
            assertThat(restored).isEqualTo(AmountData(Amount(1)))
        }

        @Test
        fun `properly serialize null value`() {
            // when
            val json = objectMapper.writeValueAsString(AmountData(null))
            val restored = objectMapper.readValue(json, AmountData::class.java)

            // then
            assertThat(restored).isNotNull()
            assertThat(restored.amount).isNull()
        }


    }


    data class ProductData(val productId: ProductId?)
    data class AmountData(val amount: Amount?)

}


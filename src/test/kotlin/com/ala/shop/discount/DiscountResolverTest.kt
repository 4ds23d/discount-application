package com.ala.shop.discount

import com.ala.shop.core.Percentage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiscountResolverTest {
    private val serializer = DiscountSerializer()

    @Test
    fun `QuantityBasedDiscount can be restored from json`() {
        // when
        val restored = serializer.deserialize("{\"@type\":\"QuantityBasedDiscountV1\",\"configuration\":[{\"from\":0,\"to\":9,\"percentage\":{\"value\":0}},{\"from\":10,\"to\":19,\"percentage\":{\"value\":5}},{\"from\":20,\"to\":49,\"percentage\":{\"value\":10}},{\"from\":50,\"to\":null,\"percentage\":{\"value\":15}}]}")

        // then
        assertThat(restored).isEqualTo(
            QuantityBasedDiscount(listOf(
            Configuration(0, 9, Percentage.zero()),
            Configuration(10, 19, Percentage.create("5")),
            Configuration(20, 49, Percentage.create("10")),
            Configuration(50, null, Percentage.create("15")),
        ))
        )
    }

    @Test
    fun `Percentage can be restored from json`() {
        // when
        val restored = serializer.deserialize("{\"@type\":\"PercentageV1\",\"percentage\":{\"value\":5}}")

        // then
        assertThat(restored).isEqualTo(PercentageDiscount(Percentage.create("5")))
    }
}
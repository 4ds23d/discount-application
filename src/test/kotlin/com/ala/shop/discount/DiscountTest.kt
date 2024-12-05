package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.core.Percentage
import com.ala.shop.core.ProductId
import com.ala.shop.product.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class DiscountTest {

    private val discountFacade = DiscountConfigurator().buildDiscountFacade(InMemoryDiscountConfiguration())
    private val product = aProduct()

    @Test
    fun `add percentage based discount`() {
        // given
        discountFacade.addDiscount(product.productId, PercentageDiscount(Percentage.create("5")))

        // when
        val discount = discountFacade.computeDiscountAmount(product, Amount(2))

        // then
        assertThat(discount).isEqualTo(Money.create("10"))
    }

    @ParameterizedTest
    @CsvSource(
        "1, 0",
        "9, 0",
        "10, 5",
        "11, 10",
        "19, 50",
        "20, 60",
        "59, 500",
    )
    fun `add quantity based discount`(orderAmount: Int, expectedDiscount: String) {
        // given
        discountFacade.addDiscount(product.productId, QuantityBasedDiscount(listOf(
            Configuration(0, 9, Percentage.zero()),
            Configuration(10, 19, Percentage.create("5")),
            Configuration(20, 49, Percentage.create("10")),
            Configuration(50, null, Percentage.create("15")),
            ))
        )

        // when
        val discount = discountFacade.computeDiscountAmount(product, Amount(orderAmount))

        // then
        assertThat(discount).isEqualTo(Money.create(expectedDiscount))
    }

    @Test
    fun `on multiple discounts max discount is assigned`() {
        // given
        discountFacade.addDiscount(product.productId, PercentageDiscount(Percentage.create("5")))
        discountFacade.addDiscount(product.productId, PercentageDiscount(Percentage.create("10")))
        discountFacade.addDiscount(product.productId, PercentageDiscount(Percentage.create("50")))

        // when
        val discount = discountFacade.computeDiscountAmount(product, Amount(2))

        // then
        assertThat(discount).isEqualTo(Money.create("100"))
    }

    @Test
    fun `no configuration for product`() {
        // when
        val discount = discountFacade.computeDiscountAmount(product, Amount(2))

        // then
        assertThat(discount).isEqualTo(Money.zero())
    }

    private fun aProduct(): Product {
        return Product(ProductId.newId(), Money.create("100"))
    }
}



package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.product.Product

class DiscountCalculator (
    private val discountResolver: DiscountResolver,
    private val cumulativeDiscountStrategy: CumulativeDiscountStrategy
    ) {

    fun computeDiscount(product: Product, orderAmount: Amount): Money {
        val discounts = discountResolver.findForProduct(product.productId)

        val maxDiscount = product.price * orderAmount

        val computedDiscounts = discounts
            .map { it.compute(product.price, orderAmount) }
            .filter { it <= maxDiscount }

        return cumulativeDiscountStrategy.pickDiscount(computedDiscounts)
    }
}
package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.core.ProductId
import com.ala.shop.product.Product

class DiscountFacade(
    private val calculator: DiscountCalculator,
    private val discountResolver: DiscountResolver
) {
    fun computeDiscountAmount(product: Product, orderAmount: Amount): Money {
        return calculator.computeDiscountAmount(product, orderAmount)
    }

    fun addDiscount(productId: ProductId, discount: String) {
        discountResolver.addDiscount(productId, discount)
    }

    fun addDiscount(productId: ProductId, discount: Discount) {
        discountResolver.addDiscount(productId, discount)
    }
}
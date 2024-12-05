package com.ala.shop.discount

import com.ala.shop.core.ProductId

interface DiscountRepository {
    fun registerDiscount(productId: ProductId, discount: String)
    fun findDiscounts(productId: ProductId): List<String>
}
package com.ala.shop.discount

import com.ala.shop.core.ProductId

class DiscountResolver (
    private val discountRepository: DiscountRepository,
    private val serializer: DiscountSerializer
) {

    fun addDiscount(productId: ProductId, discount: Discount) {
        discountRepository.registerDiscount(productId, serializer.serialize(discount))
    }

    fun addDiscount(productId: ProductId, discount: String) {
        verifyUnsafeConfiguration(discount)
        discountRepository.registerDiscount(productId, discount)
    }

    fun findForProduct(product: ProductId): List<Discount> {
        return discountRepository.findDiscounts(product)
            .map { serializer.deserialize(it) }
    }

    private fun verifyUnsafeConfiguration(discount: String) {
        serializer.deserialize(discount) // simply verification if we cannot deserialize it then we throw exception
    }
}
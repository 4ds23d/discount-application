package com.ala.shop.discount

import com.ala.shop.core.ProductId
import java.util.concurrent.ConcurrentHashMap

class InMemoryDiscountConfiguration: DiscountRepository {
    private val repo = ConcurrentHashMap<ProductId, MutableList<String>>()

    override fun registerDiscount(productId: ProductId, discount: String) {
        val productDiscounts: MutableList<String> = repo.getOrDefault(productId, mutableListOf())
        productDiscounts.addLast(discount)
        repo.put(productId, productDiscounts)
    }

    override fun findDiscounts(productId: ProductId): List<String> {
        return repo.getOrDefault(productId, listOf())
    }

}
package com.ala.shop.discount.infractracture

import com.ala.shop.core.ProductId
import com.ala.shop.discount.DiscountRepository
import org.springframework.context.annotation.Configuration

@Configuration
class DiscountRepositoryJpaImpl(
    private val repository: DiscountEntityRepository
): DiscountRepository {

    override fun registerDiscount(productId: ProductId, discount: String) {
        val discountEntity = DiscountEntity(
            productId = productId.toString(),
            configValue = discount
        )
        repository.save(discountEntity)
    }

    override fun findDiscounts(productId: ProductId): List<String> {
        return repository.findByProductId(productId.toString()).map { it.configValue }
    }
}
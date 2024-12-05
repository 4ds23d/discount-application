package com.ala.shop.discount.infractracture

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DiscountEntityRepository: JpaRepository<DiscountEntity, Long> {

    fun findByProductId(productId: String): List<DiscountEntity>
}
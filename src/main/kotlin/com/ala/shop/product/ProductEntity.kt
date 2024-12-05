package com.ala.shop.product

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class ProductEntity(
    @Id
    @Column(length = 60, nullable = false)
    val productId: String,

    @Column(columnDefinition="TEXT", nullable = false)
    val description: String,

    @Column(columnDefinition="TEXT", nullable = false)
    val basePrice: String
) {
}
package com.ala.shop.discount.infractracture

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class DiscountEntity(

    @GeneratedValue
    @Id
    val id: Long = 0,

    @Column(length = 60, nullable = false)
    val productId: String,

    @Column(columnDefinition="TEXT", nullable = false)
    val configValue: String
) {
}
package com.ala.shop.core

import java.util.*

data class ProductId(val id: UUID) {
    companion object {
        fun newId() = ProductId(UUID.randomUUID())
        fun from(input: String) = ProductId(UUID.fromString(input))
    }

    override fun toString() = id.toString()
}
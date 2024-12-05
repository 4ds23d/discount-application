package com.ala.shop.product

import com.ala.shop.core.Money
import com.ala.shop.core.ProductId


data class Product(val productId: ProductId,
                   val price: Money) {
}
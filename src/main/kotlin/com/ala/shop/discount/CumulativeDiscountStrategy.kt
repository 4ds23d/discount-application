package com.ala.shop.discount

import com.ala.shop.core.Money

interface CumulativeDiscountStrategy {
    fun pickDiscount(discounts: List<Money>): Money
}


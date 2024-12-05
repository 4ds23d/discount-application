package com.ala.shop.discount

import com.ala.shop.core.Money

class PickMaximumDiscount: CumulativeDiscountStrategy {
    override fun pickDiscount(discounts: List<Money>): Money {
        return discounts.maxOrNull() ?: Money.zero()
    }
}
package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money

class NoDiscount: Discount {
    override fun compute(price: Money, amount: Amount): Money {
        return Money.zero()
    }
}
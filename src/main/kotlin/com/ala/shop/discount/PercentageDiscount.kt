package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.core.Percentage

data class PercentageDiscount(val percentage: Percentage) : Discount {
    override fun compute(price: Money, amount: Amount): Money {
        return price.percentage(percentage) * amount
    }
}
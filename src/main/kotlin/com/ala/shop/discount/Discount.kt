package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    defaultImpl = NoDiscount::class,
    property = "@type")
@JsonSubTypes(
    JsonSubTypes.Type(value = PercentageDiscount::class, name = "PercentageV1"),
    JsonSubTypes.Type(value = QuantityBasedDiscount::class, name = "QuantityBasedDiscountV1"),
)
interface Discount {
    fun compute(price: Money, amount: Amount): Money
}


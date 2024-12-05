package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.core.Percentage

data class Configuration(val from: Int, val to: Int?, val percentage: Percentage)

data class QuantityBasedDiscount(val configuration: List<Configuration>): Discount {

    override fun compute(price: Money, amount: Amount): Money {
        var discount = Money.zero()
        val cfgIterator = ConfigurationIterator(configuration)
        for (amountVal in 1..amount.value) {
            val cfg = cfgIterator.nextFor(amountVal)
            cfg?.let { c ->
                discount += price.percentage(c.percentage)
            }
        }
        return discount
    }

    data class ConfigurationIterator(var configuration: List<Configuration>,
                                     var cfgIdx: Int = 0) {
        init {
            configuration = configuration.sortedBy { it.from }
        }

        fun nextFor(amount: Int): Configuration? {
            for (idx in cfgIdx..< configuration.size) {
                val cfg = configuration[idx]
                if (amount < cfg.from) {
                    continue
                }

                if (cfg.to == null) {
                    cfgIdx = idx
                    return cfg
                }
                if (amount <= cfg.to) {
                    cfgIdx = idx
                    return cfg
                }
            }
            return null
        }
    }
}
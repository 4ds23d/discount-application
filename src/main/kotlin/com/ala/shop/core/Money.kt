package com.ala.shop.core

import java.math.BigDecimal

data class Money constructor (
    private val amount: BigDecimal
): Comparable<Money>{

    companion object {
        fun create(value: String): Money {
            return Money(BigDecimal(value))
        }

        fun zero(): Money {
            return Money(BigDecimal.ZERO)
        }
    }

    operator fun plus(value: Money): Money {
        return Money(amount.add(value.amount))
    }

    operator fun minus(increment: Money): Money {
        return Money(amount.minus(increment.amount))
    }

    operator fun times(amount: Amount): Money {
        return Money(this.amount.multiply(BigDecimal(amount.value)))
    }

    override fun compareTo(other: Money): Int {
        return this.amount.compareTo(other.amount)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Money) return false
        return this.amount.compareTo(other.amount) == 0
    }

    override fun toString(): String {
        return amount.toString()
    }

    fun percentage(percentage: Percentage): Money {
        val newValue =  this.amount.multiply(percentage.value).divide(BigDecimal("100"))
        return Money(newValue)
    }

    override fun hashCode(): Int {
        return amount.stripTrailingZeros().hashCode()
    }
}
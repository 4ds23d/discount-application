package com.ala.shop.core

import java.math.BigDecimal

class Percentage private constructor(
    val value: BigDecimal
) {
    companion object {
        fun create(value: String): Percentage {
            return Percentage(BigDecimal(value))
        }

        fun zero(): Percentage {
            return Percentage(BigDecimal.ZERO)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Percentage) return false
        return value.compareTo(other.value) == 0
    }

    override fun hashCode(): Int {
        return value.stripTrailingZeros().hashCode()
    }
}
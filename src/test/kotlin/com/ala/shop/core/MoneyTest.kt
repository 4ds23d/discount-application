package com.ala.shop.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoneyTest {
    
    @Test
    fun `equal should work`() {
        // given
        val m0 = Money.create("0")
        val m00 = Money.create("0.0")

        // when
        assertThat(m0 == m00).isTrue()
        assertThat(m00 == m00).isTrue()
        assertThat(m00 == m0).isTrue()
        assertThat(m0 == m0).isTrue()
        assertThat(m0 == Money.create("1.0")).isFalse()
    }

    @Test
    fun `hashcode should work`() {
        // given
        val m0 = Money.create("0")
        val m00 = Money.create("0.0")

        // when
        assertThat(m0.hashCode()).isEqualTo(m00.hashCode())
        assertThat(Money.create("123").hashCode()).isNotEqualTo(m00.hashCode())
    }

    @Test
    fun `compute percentage of money`() {
        // given
        val percentage = Percentage.create("10")
        val money = Money.create("100")

        // when
        val newMoney = money.percentage(percentage)

        // then
        assertThat(newMoney == Money.create("10"))
    }

    @Test
    fun `minus operator is defined`() {
        // given
        val price = Money.create("100")

        // when
        assertThat(price - Money.create("10")).isEqualTo(Money.create("90"))
        assertThat(price - Money.create("100")).isEqualTo(Money.create("0"))
    }

    @Test
    fun `times operator is defined`() {
        // given
        val price = Money.create("100")

        // when
        assertThat(price * Amount(1)).isEqualTo(Money.create("100"))
        assertThat(price * Amount(10)).isEqualTo(Money.create("1000"))
    }
}
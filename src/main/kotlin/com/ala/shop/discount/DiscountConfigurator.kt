package com.ala.shop.discount

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscountConfigurator {

    @Bean
    fun buildDiscountFacade(discountRepository: DiscountRepository): DiscountFacade {
        val discountResolver = DiscountResolver(discountRepository, DiscountSerializer())
        val discountCalculator = DiscountCalculator(discountResolver, PickMaximumDiscount())
        return DiscountFacade(discountCalculator, discountResolver)
    }
}
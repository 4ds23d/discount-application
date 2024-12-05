package com.ala.shop.config

import com.ala.shop.core.StringToAmountConverter
import com.ala.shop.core.StringToProductIdConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConvertersConfiguration {

    @Bean
    fun stringToProductIdConverter(): StringToProductIdConverter {
        return StringToProductIdConverter()
    }

    @Bean
    fun stringToAmountConverter(): StringToAmountConverter {
        return StringToAmountConverter()
    }
}
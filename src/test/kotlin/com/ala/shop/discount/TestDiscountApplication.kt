package com.ala.shop.discount

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<DiscountApplication>().with(TestcontainersConfiguration::class).run(*args)
}

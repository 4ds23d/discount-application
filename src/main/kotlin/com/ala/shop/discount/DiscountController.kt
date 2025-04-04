package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.Percentage
import com.ala.shop.core.ProductId
import com.ala.shop.product.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/discount")
class DiscountController(
    private val productService: ProductService,
    private val discountFacade: DiscountFacade
) {
    private val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @GetMapping("{productId}")
    fun getDiscount(@PathVariable productId: ProductId, @RequestParam amount: Amount): ResponseEntity<DiscountPrice> {
        logger.info("Get discount for product {} and {}", productId, amount)
        return productService.findProduct(productId)
            .map {
                val discount = discountFacade.computeDiscountAmount(it, amount)
                val response =  DiscountPrice(productId, amount, discount.toString())
                ResponseEntity.ok(response)
            }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("{productId}/percentage")
    fun addDiscountPercentage(@PathVariable productId: ProductId, @RequestBody request: PercentageDiscountRequest): ResponseEntity<Void> {
        logger.info("Add discount percentage configuration: {} for product {}", request, productId)
        val discount = PercentageDiscount(Percentage.create(request.amount))

        discountFacade.addDiscount(productId, discount)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("{productId}/quantity")
    fun addDiscountQuantityBased(@PathVariable productId: ProductId, @RequestBody request: List<QuantityBasedDiscountRequest>): ResponseEntity<Void> {
        logger.info("Add percentage discount configuration: {} for product {}", request, productId)
        val discount = QuantityBasedDiscount(configuration = request.map { Configuration(it.from, it.to, Percentage.create(it.percentage)) })
        discountFacade.addDiscount(productId, discount)
        return ResponseEntity.accepted().build()
    }
}

data class DiscountPrice(val productId: ProductId, val amount: Amount, val discount: String)
data class PercentageDiscountRequest(val amount: String)
data class QuantityBasedDiscountRequest(val from: Int, val to: Int, val percentage: String)

package com.ala.shop.discount

import com.ala.shop.core.Amount
import com.ala.shop.core.ProductId
import com.ala.shop.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/discount")
class DiscountController(
    private val productService: ProductService,
    private val discountFacade: DiscountFacade
) {

    @GetMapping("{productId}")
    fun getDiscount(@PathVariable productId: ProductId, @RequestParam amount: Amount): ResponseEntity<DiscountPrice> {
        return productService.findProduct(productId)
            .map {
                val discount = discountFacade.computeDiscountAmount(it, amount)
                val response =  DiscountPrice(productId, amount, discount.toString())
                ResponseEntity.ok(response)
            }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("{productId}")
    fun addDiscount(@PathVariable productId: ProductId, @RequestBody config: String): ResponseEntity<Void> {
        discountFacade.addDiscount(productId, config)
        return ResponseEntity.accepted().build()
    }
}

data class DiscountPrice(val productId: ProductId, val amount: Amount, val discount: String)
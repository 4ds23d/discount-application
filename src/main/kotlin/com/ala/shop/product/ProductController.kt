package com.ala.shop.product

import com.ala.shop.core.ProductId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    private val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @GetMapping("{productId}/description")
    fun getProductDescription(@PathVariable productId: ProductId): ResponseEntity<ProductInformation> {
        return productService.findProductDescription(productId)
            .map { ResponseEntity.ok(ProductInformation(productId, it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("{productId}")
    fun addProduct(@PathVariable productId: ProductId, @RequestBody addProduct: AddProduct): ResponseEntity<Void> {
        logger.info("Add product {} {}", productId, addProduct)
        productService.addProduct(productId, addProduct)
        return ResponseEntity.accepted().build()
    }
}

data class ProductInformation(val id: ProductId, val description: String)
data class AddProduct(val description: String, val price: String)

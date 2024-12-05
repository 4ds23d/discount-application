package com.ala.shop.product

import com.ala.shop.core.Money
import com.ala.shop.core.ProductId
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    private val productEntityRepository: ProductEntityRepository) {

    fun findProduct(productId: ProductId): Optional<Product> {
        return productEntityRepository.findById(productId.toString())
            .map { Product(productId, Money.create(it.basePrice)) }
    }

    fun findProductDescription(productId: ProductId): Optional<String> {
        return productEntityRepository.findById(productId.toString())
            .map { it.description }
    }

    fun addProduct(productId: ProductId, addProduct: AddProduct) {
        val product = ProductEntity(
            productId = productId.toString(),
            description = addProduct.description,
            basePrice = addProduct.price
        )
        productEntityRepository.save(product)
    }
}
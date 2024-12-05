package com.ala.shop.product

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductEntityRepository: CrudRepository<ProductEntity, String> {
}
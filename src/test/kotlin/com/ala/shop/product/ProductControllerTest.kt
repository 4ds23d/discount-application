package com.ala.shop.product

import com.ala.shop.config.ConvertersConfiguration
import com.ala.shop.config.ObjectMapperConfiguration
import com.ala.shop.core.ProductId
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(ProductController::class)
@Import(
    ConvertersConfiguration::class,
    ObjectMapperConfiguration::class
)
class ProductControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockitoBean
    lateinit var productService: ProductService
    private val productId = ProductId.from("67fe40ed-abb4-454f-95f6-95ba6168fda5")

    @Test
    fun addProduct() {
        // when
        mockMvc.perform(
            post("/api/v1/products/67fe40ed-abb4-454f-95f6-95ba6168fda5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"description\",\"price\":\"100\"}")
        )
            .andExpect(status().isAccepted)

        // then
        Mockito.verify(productService, Mockito.times(1)).addProduct(productId, AddProduct("description", "100"))
    }

    @Test
    fun fetchProductDescription() {
        Mockito.`when`(productService.findProductDescription(productId))
            .thenReturn(Optional.ofNullable("Description"))

        // when
        mockMvc.perform(get("/api/v1/products/67fe40ed-abb4-454f-95f6-95ba6168fda5/description"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("67fe40ed-abb4-454f-95f6-95ba6168fda5"))
            .andExpect(jsonPath("$.description").value("Description"))
    }
}
package com.ala.shop.discount

import com.ala.shop.config.ConvertersConfiguration
import com.ala.shop.config.ObjectMapperConfiguration
import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.core.ProductId
import com.ala.shop.product.Product
import com.ala.shop.product.ProductService
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


@WebMvcTest(DiscountController::class)
@Import(
    ConvertersConfiguration::class,
    ObjectMapperConfiguration::class
)
class DiscountControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockitoBean
    lateinit var discountFacade: DiscountFacade

    @MockitoBean
    lateinit var productService: ProductService

    @Test
    fun computeDiscount() {
        // given
        val productId = ProductId.from("67fe40ed-abb4-454f-95f6-95ba6168fda5")
        val product = Product(productId, Money.create("10"))
        Mockito.`when`(productService.findProduct(productId))
            .thenReturn(Optional.of(product))

        Mockito.`when`(discountFacade.computeDiscountAmount(product, Amount(10)))
            .thenReturn(Money.create("20"))

        // when
        mockMvc.perform(get("/api/v1/discount/67fe40ed-abb4-454f-95f6-95ba6168fda5?amount=10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.productId").value("67fe40ed-abb4-454f-95f6-95ba6168fda5"))
            .andExpect(jsonPath("$.amount").value("10"))
            .andExpect(jsonPath("$.discount").value("20"))
    }

    @Test
    fun registerDiscount() {
        // when
        mockMvc.perform(
            post("/api/v1/discount/67fe40ed-abb4-454f-95f6-95ba6168fda5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"@type\":\"PercentageV1\",\"percentage\":{\"value\":5}}")
        )
            .andExpect(status().isAccepted)

        // then
        val productId = ProductId.from("67fe40ed-abb4-454f-95f6-95ba6168fda5")
        Mockito.verify(discountFacade, Mockito.times(1)).addDiscount(productId, "{\"@type\":\"PercentageV1\",\"percentage\":{\"value\":5}}")
    }
}
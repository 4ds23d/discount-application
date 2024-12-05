package com.ala.shop

import com.ala.shop.core.Amount
import com.ala.shop.core.Money
import com.ala.shop.core.Percentage
import com.ala.shop.core.ProductId
import com.ala.shop.discount.DiscountFacade
import com.ala.shop.discount.PercentageDiscount
import com.ala.shop.product.AddProduct
import com.ala.shop.product.ProductService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class AcceptanceIntTest {

    @Autowired
    lateinit var discountFacade: DiscountFacade
    @Autowired
    lateinit var productService: ProductService


    @Test
    fun `when product is registered in system the discount can be computed and the description can be retrieved`() {
        // given
        val productId = ProductId.newId()
        val addProduct = AddProduct("Description", "100")

        // when
        productService.addProduct(productId, addProduct)

        // then
        assertThat(productService.findProductDescription(productId)).hasValue("Description")

        // and when we want to compute discount
        val discount =
            discountFacade.computeDiscountAmount(productService.findProduct(productId).orElseThrow(), Amount(1))

        // then
        assertThat(discount).isEqualTo(Money.zero())
    }

    @Test
    fun `compute discount`() {
        // given
        val productId = ProductId.newId()
        val addProduct = AddProduct("Description", "100")

        // and product is registered
        productService.addProduct(productId, addProduct)

        // and discount policy is stored in database
        discountFacade.addDiscount(productId, PercentageDiscount(Percentage.create("5")))


        // and when we want to compute discount
        val discount =
            discountFacade.computeDiscountAmount(productService.findProduct(productId).orElseThrow(), Amount(1))

        // then
        assertThat(discount).isEqualTo(Money.create("5"))
    }

}
package com.gilvano.util

import com.gilvano.domain.Product
import com.gilvano.dto.ProductRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProductConverterUtilTest {

    @Test
    fun `when toProductResponse is call, should return a ProductResponse with all data`() {
        val product = Product(id = 1, name = "product name", price = 10.90, quantityInStock = 10)
        val productResponse = product.toProductResponse()

        assertEquals(product.id, productResponse.id)
        assertEquals(product.name, productResponse.name)
        assertEquals(product.price, productResponse.price)
        assertEquals(product.quantityInStock, productResponse.quantityInStock)
    }

    @Test
    fun `when toDomain is call, should return a Product with all data`() {
        val productRequest = ProductRequest(name = "product name", price = 10.90, quantityInStock = 10)
        val product = productRequest.toDomain()

        assertEquals(null, product.id)
        assertEquals(productRequest.name, product.name)
        assertEquals(productRequest.price, product.price)
        assertEquals(productRequest.quantityInStock, product.quantityInStock)
    }
}
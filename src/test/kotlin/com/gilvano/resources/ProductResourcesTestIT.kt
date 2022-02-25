package com.gilvano.resources

import com.gilvano.FindByIdServiceRequest
import com.gilvano.ProductServiceRequest
import com.gilvano.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductResourcesTestIT(
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        Assertions.assertEquals(2, response.id)
        Assertions.assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {
        val request = FindByIdServiceRequest.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        Assertions.assertEquals(1, response.id)
        Assertions.assertEquals("product 1", response.name)
    }
}
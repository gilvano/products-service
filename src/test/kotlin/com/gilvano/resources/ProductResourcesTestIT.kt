package com.gilvano.resources

import com.gilvano.ProductServiceRequest
import com.gilvano.ProductServiceUpdateRequest
import com.gilvano.ProductsServiceGrpc.ProductsServiceBlockingStub
import com.gilvano.RequestById
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductResourcesTestIT(
    private val flyway: Flyway,
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {
    @BeforeEach
    fun setUp() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        Assertions.assertEquals(3, response.id)
        Assertions.assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product 1")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val description = "Produto ${request.name} já cadastrado no sistema."


        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.create(request)
        }

        Assertions.assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        Assertions.assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {
        val request = RequestById.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        Assertions.assertEquals(1, response.id)
        Assertions.assertEquals("product 1", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with invalid id a ProductNotFound is returned`() {
        val request = RequestById.newBuilder()
            .setId(10)
            .build()

        val description = "Produto com id ${request.id} não encontrado"

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.findById(request)
        }

        Assertions.assertEquals(Status.NOT_FOUND.code, response.status.code)
        Assertions.assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with valid data a success is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(2L)
            .setName("product updated")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.update(request)

        Assertions.assertEquals(2, response.id)
        Assertions.assertEquals("product updated", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1)
            .setName("product 2")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val description = "Produto ${request.name} já cadastrado no sistema."


        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        Assertions.assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        Assertions.assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid id a ProductNotFound is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1)
            .setName("product 2")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val description = "Produto ${request.name} já cadastrado no sistema."

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        Assertions.assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        Assertions.assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with valid id a success is returned`() {
        val request = RequestById.newBuilder()
            .setId(2L)
            .build()

        assertDoesNotThrow {
            productsServiceBlockingStub.delete(request)
        }
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with invalid id a ProductNotFound is returned`() {
        val request = RequestById.newBuilder()
            .setId(22L)
            .build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.delete(request)
        }
        val description = "Produto com id ${request.id} não encontrado"
        Assertions.assertEquals(Status.NOT_FOUND.code, response.status.code)
        Assertions.assertEquals(description, response.status.description)
    }
}
package com.gilvano.services.impl

import com.gilvano.domain.Product
import com.gilvano.dto.ProductRequest
import com.gilvano.dto.ProductUpdateRequest
import com.gilvano.exceptions.AlreadyExistsException
import com.gilvano.exceptions.ProductNotFoundException
import com.gilvano.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

internal class ProductServiceImplTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with valid data a Product is returned`() {
        val productInput = Product(id = null, name = "Product Name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "Product Name", price = 10.00, quantityInStock = 5)


        `when`(productRepository.save(productInput))
            .thenReturn(productOutput)

        val productRequest = ProductRequest(name = "Product Name", price = 10.00, quantityInStock = 5)

        val productResponse = productService.create(productRequest)

        assertEquals(productInput.name, productResponse.name)
    }

    @Test
    fun `when create method is call with duplicated product name, throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "Product Name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "Product Name", price = 10.00, quantityInStock = 5)


        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productRequest = ProductRequest(name = "Product Name", price = 10.00, quantityInStock = 5)

        assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.create(productRequest)
        }
    }

    @Test
    fun `when findById method is call with valid id a Product is returned`() {
        val productInput = 1L
        val productOutput = Product(id = 1, name = "Product Name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findById(productInput))
            .thenReturn(Optional.of(productOutput))


        val productResponse = productService.findById(productInput)

        assertEquals(productInput, productResponse.id)
        assertEquals(productOutput.name, productResponse.name)
    }

    @Test
    fun `when findById method is call with invalid id, throws ProductNotFoundException`() {
        val id = 1L
        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.findById(id)
        }
    }

    @Test
    fun `when update method is call with duplicated product name, throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "Product Name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "Product Name", price = 10.00, quantityInStock = 5)


        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productRequest = ProductUpdateRequest(id = 1, name = "Product Name", price = 10.00, quantityInStock = 5)

        assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.update(productRequest)
        }
    }

    @Test
    fun `when update method is call with invalid id, throws ProductNotFoundException`() {
        val productRequest = ProductUpdateRequest(id = 2, name = "Product Name", price = 10.00, quantityInStock = 5)
        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.update(productRequest)
        }
    }

    @Test
    fun `when update method is call with valid data a Product is returned`() {
        val productOriginal = Product(id = 1, name = "Product Name", price = 10.00, quantityInStock = 5)
        val productUpdated = Product(id = 1, name = "updated product", price = 11.00, quantityInStock = 5)


        `when`(productRepository.findById(productOriginal.id!!))
            .thenReturn(Optional.of(productOriginal))
        `when`(productRepository.update(productUpdated))
            .thenReturn(productUpdated)

        val productRequest = ProductUpdateRequest(id = 1, name = "updated product", price = 11.00, quantityInStock = 5)

        val productResponse = productService.update(productRequest)

        assertEquals(productRequest.name, productResponse.name)
    }

    @Test
    fun `when delete method is call with valid id a Product is deleted`() {
        val productId = 1L
        val productOutput = Product(id = productId, name = "Product Name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findById(productId)).thenReturn(Optional.of(productOutput))

        assertDoesNotThrow{
            productService.delete(productId)
        }
    }

    @Test
    fun `when delete method is call with invalid id, throws ProductNotFoundException`() {
        val productId = 1L

        `when`(productRepository.findById(productId)).thenReturn(Optional.empty())

        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.delete(productId)
        }
    }

    @Test
    fun `when findAll method is call a Product list is returned`() {
        val products = listOf(
            Product(id = 1, name = "Product 1", price = 10.00, quantityInStock = 5),
            Product(id = 2, name = "Product 2", price = 12.00, quantityInStock = 8)
        )

        `when`(productRepository.findAll())
            .thenReturn(products)


        val productResponse = productService.findAll()

        assertEquals(products[0].name, productResponse[0].name)
    }

    @Test
    fun `when findAll method is call without products a empty list of Product is returned`() {
        val products = emptyList<Product>()
        `when`(productRepository.findAll())
            .thenReturn(emptyList())

        val productResponse = productService.findAll()

        assertEquals(products.size, productResponse.size)
    }
}
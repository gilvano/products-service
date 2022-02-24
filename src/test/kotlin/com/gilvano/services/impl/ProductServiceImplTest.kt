package com.gilvano.services.impl

import com.gilvano.domain.Product
import com.gilvano.dto.ProductRequest
import com.gilvano.exceptions.AlreadyExistsException
import com.gilvano.exceptions.ProductNotFoundException
import com.gilvano.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
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
}
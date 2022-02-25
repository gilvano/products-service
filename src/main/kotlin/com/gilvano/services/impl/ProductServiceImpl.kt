package com.gilvano.services.impl

import com.gilvano.dto.ProductRequest
import com.gilvano.dto.ProductResponse
import com.gilvano.dto.ProductUpdateRequest
import com.gilvano.exceptions.AlreadyExistsException
import com.gilvano.exceptions.ProductNotFoundException
import com.gilvano.repository.ProductRepository
import com.gilvano.services.ProductService
import com.gilvano.util.toDomain
import com.gilvano.util.toProductResponse
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {
    override fun create(request: ProductRequest): ProductResponse {
        verifyName(request.name)
        return productRepository.save(request.toDomain()).toProductResponse()
    }

    override fun findById(id: Long): ProductResponse {
        val findById = productRepository.findById(id)
        findById.orElseThrow{ ProductNotFoundException(id)}
        return findById.get().toProductResponse()
    }

    override fun update(request: ProductUpdateRequest): ProductResponse {
        verifyName(request.name)
        val product = productRepository.findById(request.id)
            .orElseThrow { ProductNotFoundException(request.id) }
        val copy = product.copy(
            name = request.name,
            price = request.price,
            quantityInStock = request.quantityInStock
        )

        return productRepository.update(copy).toProductResponse()
    }

    private fun verifyName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}
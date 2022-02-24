package com.gilvano.services.impl

import com.gilvano.dto.ProductRequest
import com.gilvano.dto.ProductResponse
import com.gilvano.exceptions.AlreadyExistsException
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

    private fun verifyName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}
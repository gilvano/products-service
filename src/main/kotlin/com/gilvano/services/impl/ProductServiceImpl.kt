package com.gilvano.services.impl

import com.gilvano.dto.ProductRequest
import com.gilvano.repository.ProductRepository
import com.gilvano.services.ProductService
import com.gilvano.util.toDomain
import com.gilvano.util.toProductResponse
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {
    override fun create(request: ProductRequest) =
        productRepository.save(request.toDomain()).toProductResponse()
}
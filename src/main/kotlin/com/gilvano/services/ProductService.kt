package com.gilvano.services

import com.gilvano.domain.Product
import com.gilvano.dto.ProductRequest
import com.gilvano.dto.ProductResponse

interface ProductService  {
    fun create(request: ProductRequest): ProductResponse
}
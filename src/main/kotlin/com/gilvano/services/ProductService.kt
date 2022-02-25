package com.gilvano.services

import com.gilvano.dto.ProductRequest
import com.gilvano.dto.ProductResponse
import com.gilvano.dto.ProductUpdateRequest

interface ProductService  {
    fun create(request: ProductRequest): ProductResponse
    fun findById(id: Long): ProductResponse
    fun update(request: ProductUpdateRequest): ProductResponse
    fun delete(id: Long)
}
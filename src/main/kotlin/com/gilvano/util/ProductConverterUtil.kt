package com.gilvano.util

import com.gilvano.domain.Product
import com.gilvano.dto.ProductRequest
import com.gilvano.dto.ProductResponse

fun Product.toProductResponse() = ProductResponse(
        id = id!!,
        name = name,
        price = price,
        quantityInStock = quantityInStock
    )


fun ProductRequest.toDomain() = Product(
        id = null,
        name = name,
        price = price,
        quantityInStock = quantityInStock
    )



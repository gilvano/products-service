package com.gilvano.dto

data class ProductUpdateRequest(
    val id: Long,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)

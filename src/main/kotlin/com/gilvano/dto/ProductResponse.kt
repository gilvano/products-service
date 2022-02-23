package com.gilvano.dto

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)

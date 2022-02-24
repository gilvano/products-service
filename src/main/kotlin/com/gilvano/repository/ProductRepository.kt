package com.gilvano.repository

import com.gilvano.domain.Product
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByNameIgnoreCase(name: String): Product?
}
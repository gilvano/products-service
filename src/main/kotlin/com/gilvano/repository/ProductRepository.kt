package com.gilvano.repository

import com.gilvano.domain.Product
import io.micronaut.data.jpa.repository.JpaRepository
import jakarta.inject.Singleton

@Singleton
interface ProductRepository : JpaRepository<Product, Long> {
}
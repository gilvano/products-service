package com.gilvano.util

import com.gilvano.ProductServiceRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ValidationUtilTest{

    @Test
    fun `when validatePayload method is call with valid data, should not throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        assertDoesNotThrow() {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid product name, should throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid product price, should throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(-20.99)
            .setQuantityInStock(10)
            .build()

        assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with null payload, should throw exception`() {
        assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(null)
        }
    }
}
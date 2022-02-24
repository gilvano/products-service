package com.gilvano.exceptions

import io.grpc.Status

class ProductNotFoundException(private val productId: Long) : BaseBusinessException() {
    override fun errorMessage(): String {
        return "Produto com id $productId não encontrado"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.NOT_FOUND
    }
}
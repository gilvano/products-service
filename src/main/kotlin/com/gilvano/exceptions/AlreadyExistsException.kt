package com.gilvano.exceptions

import io.grpc.Status

class AlreadyExistsException(private val productName: String) : BaseBusinessException() {
    override fun errorMessage(): String {
        return "Produto $productName já cadastrado no sistema."
    }

    override fun estatusCode(): Status.Code {
        return Status.Code.ALREADY_EXISTS
    }
}
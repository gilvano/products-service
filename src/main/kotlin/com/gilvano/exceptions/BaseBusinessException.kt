package com.gilvano.exceptions

import io.grpc.Status

abstract class BaseBusinessException : RuntimeException() {
    abstract fun errorMessage(): String
    abstract fun estatusCode(): Status.Code
}
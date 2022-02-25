package com.gilvano.util

import com.gilvano.ProductServiceRequest
import com.gilvano.exceptions.InvalidArgumentException

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest {
            payload?.let{
                if (it.name.isNullOrBlank())
                    throw InvalidArgumentException("nome")

                if (it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentException("preço")

                return it
            }
            throw InvalidArgumentException("payload")
        }
    }
}
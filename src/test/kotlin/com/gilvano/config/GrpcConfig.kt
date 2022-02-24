package com.gilvano.config

import com.gilvano.ProductsServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel

@Factory
class GrpcConfig {
    @Bean
    fun productServiceBean(
        @GrpcChannel("productservice")
        channel: ManagedChannel
    ): ProductsServiceGrpc.ProductsServiceBlockingStub {
        return ProductsServiceGrpc.newBlockingStub(channel)
    }
}
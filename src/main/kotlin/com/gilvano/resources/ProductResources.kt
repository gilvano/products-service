package com.gilvano.resources

import com.gilvano.ProductServiceRequest
import com.gilvano.ProductServiceResponse
import com.gilvano.ProductsServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources() : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        super.create(request, responseObserver)
    }
}
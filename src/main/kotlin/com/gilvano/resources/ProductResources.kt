package com.gilvano.resources

import com.gilvano.ProductServiceRequest
import com.gilvano.ProductServiceResponse
import com.gilvano.ProductsServiceGrpc
import com.gilvano.dto.ProductRequest
import com.gilvano.services.ProductService
import com.gilvano.services.impl.ProductServiceImpl
import com.gilvano.util.ValidationUtil
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(
    private val productService: ProductService
) : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        val payload = ValidationUtil.validatePayload(request)
        val productRequest = ProductRequest(
            name = payload!!.name,
            price = payload.price,
            quantityInStock = payload.quantityInStock
        )
        val productResponse = productService.create(productRequest)
        val productServiceResponse = ProductServiceResponse.newBuilder()
            .setId(productResponse.id)
            .setName(productResponse.name)
            .setPrice(productResponse.price)
            .setQuantityInStock(productResponse.quantityInStock)
            .build()
        responseObserver?.onNext(productServiceResponse)
        responseObserver?.onCompleted()
    }
}
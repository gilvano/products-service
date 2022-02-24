package com.gilvano.resources

import com.gilvano.FindByIdServiceRequest
import com.gilvano.ProductServiceRequest
import com.gilvano.ProductServiceResponse
import com.gilvano.ProductsServiceGrpc
import com.gilvano.dto.ProductRequest
import com.gilvano.exceptions.BaseBusinessException
import com.gilvano.exceptions.ProductNotFoundException
import com.gilvano.services.ProductService
import com.gilvano.util.ValidationUtil
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(
    private val productService: ProductService,
) : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {
            val payload = ValidationUtil.validatePayload(request)
            val productRequest =
                ProductRequest(name = payload.name, price = payload.price, quantityInStock = payload.quantityInStock)
            val productResponse = productService.create(productRequest)
            val productServiceResponse =
                ProductServiceResponse.newBuilder().setId(productResponse.id).setName(productResponse.name)
                    .setPrice(productResponse.price).setQuantityInStock(productResponse.quantityInStock).build()
            responseObserver?.onNext(productServiceResponse)
            responseObserver?.onCompleted()
        } catch (e: BaseBusinessException) {
            responseObserver?.onError(e.statusCode().toStatus().withDescription(e.errorMessage()).asRuntimeException())
        }
    }

    override fun findById(request: FindByIdServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {
            val product = productService.findById(request!!.id)
            val productResponse = ProductServiceResponse.newBuilder()
                .setId(product.id)
                .setName(product.name)
                .setPrice(product.price)
                .setQuantityInStock(product.quantityInStock)
                .build()

            responseObserver?.onNext(productResponse)
            responseObserver?.onCompleted()
        } catch (e: ProductNotFoundException) {
            responseObserver?.onError(e.statusCode().toStatus().withDescription(e.errorMessage()).asRuntimeException())
        }
    }
}
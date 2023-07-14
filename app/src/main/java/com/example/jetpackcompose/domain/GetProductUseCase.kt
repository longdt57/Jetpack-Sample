package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.data.repository.IProductRepository
import com.example.jetpackcompose.domain.base.BaseFlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

class GetProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) : BaseFlowUseCase<Unit, List<ProductItem>>() {

    override fun fetchRemote(param: Unit): Flow<List<ProductItem>> {
        return flow {
            productRepository.fetchProducts().let {
                saveLocal(it)
                emit(it)
            }
        }
    }

    override fun getLocal(param: Unit): Flow<List<ProductItem>> {
        return productRepository.getLocalProducts().take(1)
    }

    private fun saveLocal(items: List<ProductItem>) {
        productRepository.clearAndSaveAllProducts(items)
    }
}
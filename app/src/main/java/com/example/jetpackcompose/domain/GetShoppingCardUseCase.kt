package com.example.jetpackcompose.domain

import com.example.jetpackcompose.data.repository.IProductCardRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class GetShoppingCardUseCase @Inject constructor(private val repository: IProductCardRepository) {

    fun getCardItems() = flow {
        val cardItems = repository.getAllCardItems()
        val cardItemIds = cardItems.map { it.itemId }
        val productItems = repository.getLocalProducts(cardItemIds)
        emit(Pair(cardItems, productItems))
    }

    fun getCardItemCount(): Int {
        return repository.getAllCardItems().sumOf { it.count }
    }
}
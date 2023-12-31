package com.example.jetpackcompose.ui.detail

import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.model.ProductItem
import com.example.jetpackcompose.di.DispatcherModule
import com.example.jetpackcompose.domain.AddItemToCartUseCase
import com.example.jetpackcompose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val addItemToCartUseCase: AddItemToCartUseCase,
    @DispatcherModule.IODispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel() {

    private val _addSuccess = MutableStateFlow(false)
    val addSuccess: StateFlow<Boolean> get() = _addSuccess.asStateFlow()

    fun addToCart(item: ProductItem) {
        viewModelScope.launch {
            addItemToCartUseCase(item)
                .flowOn(ioDispatcher)
                .catch { handleError(it) }
                .collect {
                    _addSuccess.update { true }
                }
        }
    }
}

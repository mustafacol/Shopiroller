package com.mustafacol.shopiroller.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacol.shopiroller.model.*
import com.mustafacol.shopiroller.repository.ShopirollerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: ShopirollerRepository
) : ViewModel() {
    val categoryState = MutableStateFlow(
        ApiState(Status.LOADING, listOf<Data>(), "")
    )
    val categoryProductsState = MutableStateFlow(
        ApiState(Status.LOADING, listOf<CategoryProductData>(),"")
    )
    var selectedCategoryId = MutableStateFlow("")
    var selectedSort= MutableStateFlow("")

    fun getCategories() {
        categoryState.value = ApiState.loading()
        viewModelScope.launch {
            repository.getCategories()
                .catch {
                    categoryState.value = ApiState.error(it.message.toString())
                }
                .collect {
                    it.data?.let { source ->
                        categoryState.value = ApiState.success(source.data)
                        selectedCategoryId.value= source.data[0].categoryId
                    }
                }
        }
    }

    fun getCategoryProducts(){
        categoryProductsState.value = ApiState.loading()
        viewModelScope.launch {
            repository.getCategoryProducts(selectedCategoryId.value,selectedSort.value)
                .catch {
                    categoryProductsState.value=ApiState.error(it.message.toString())
                }
                .collect{
                    it.data?.let { source ->
                        categoryProductsState.value = ApiState.success(source.data)
                    }
                }
        }
    }
}


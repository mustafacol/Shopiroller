package com.mustafacol.shopiroller.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacol.shopiroller.model.*
import com.mustafacol.shopiroller.repository.ShopirollerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ShopirollerRepository
): ViewModel() {

    val productDetailsState = MutableStateFlow(
        ApiState(Status.LOADING, ProductDetailData(), "")
    )

    fun getProductDetails(productId:String){
        productDetailsState.value= ApiState.loading()
        viewModelScope.launch {
            if(productId.isNotEmpty()){
                repository.getProductDetails(productId)
                    .catch {
                        productDetailsState.value = ApiState.error(it.message.toString())
                    }
                    .collect{
                        productDetailsState.value = ApiState.success(it.data?.data)
                    }
            }
        }
    }

}
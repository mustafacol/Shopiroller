package com.mustafacol.shopiroller.repository

import com.mustafacol.shopiroller.model.*
import com.mustafacol.shopiroller.network.ShopirollerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class ShopirollerRepository @Inject constructor(private val shopirollerApi: ShopirollerApi) {
    suspend fun getCategories():Flow<ApiState<Source<List<Data>>>>{
        return flow {
            val categories= shopirollerApi.getCategories()

            emit(ApiState.success(categories))
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getCategoryProducts(categoryId:String,sort:String?):Flow<ApiState<Source<List<CategoryProductData>>>>{
        return flow {
            val categoryProducts = shopirollerApi.getCategoryProducts(categoryId, sort)
            emit(ApiState.success(categoryProducts))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProductDetails(productId:String):Flow<ApiState<Source<ProductDetailData>>>{
        return flow {
            val productDetails = shopirollerApi.getProductDetails(productId)
            emit(ApiState.success(productDetails))
        }.flowOn(Dispatchers.IO)
    }
}
package com.mustafacol.shopiroller.network

import com.mustafacol.shopiroller.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopirollerApi {
    @GET("categories")
    suspend fun getCategories():Source<List<Data>>

    @GET("products/advanced-filtered")
    suspend fun getCategoryProducts(
        @Query("categoryId") categoryId:String,
        @Query("sort") sort:String?
    ):Source<List<CategoryProductData>>

    @GET("products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId:String):Source<ProductDetailData>
}
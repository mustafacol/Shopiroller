package com.mustafacol.shopiroller.model

data class CategoryProductData(
    val appId: String,
    val brand: Brand,
    val brandId: String,
    val campaignPrice: Double?,
    val category: Category,
    val categoryId: String,
    val createDate: String,
    val currency: String,
    val description: String,
    val endDate: String,
    val featuredImage: FeaturedImage,
    val id: String,
    val images: List<Image>,
    val isActive: Boolean,
    val isPublished: Boolean,
    val isUnLimitedStock: Boolean,
    val itemType: String,
    val maxQuantityPerOrder: Int,
    val orderIndex: Int,
    val price: Double,
    val publishmentDate: String,
    val shippingPrice: Double,
    val stock: Int,
    val stockCode: String,
    val title: String,
    val updateDate: String,
    val useFixPrice: Boolean,
    val variantData: List<Any>,
    val videos: List<Any>
)
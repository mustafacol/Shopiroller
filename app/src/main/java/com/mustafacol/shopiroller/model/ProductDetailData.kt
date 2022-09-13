package com.mustafacol.shopiroller.model

data class ProductDetailData(
    val appId: String = "",
    val campaignPrice: Double = 0.0,
    val category: Category = Category(),
    val categoryId: String = "",
    val createDate: String = "",
    val currency: String = "",
    val description: String = "",
    val endDate: String = "",
    val featuredImage: FeaturedImage = FeaturedImage("", ""),
    val id: String = "",
    val images: List<Image> = emptyList(),
    val isActive: Boolean = false,
    val isPublished: Boolean = false,
    val isUnLimitedStock: Boolean = false,
    val itemType: String = "",
    val maxQuantityPerOrder: Int = 0,
    val orderIndex: Int = 0,
    val price: Double = 0.0,
    val publishmentDate: String = "",
    val shippingPrice: Double = 0.0,
    val stock: Int = 0,
    val stockCode: String = "",
    val title: String = "",
    val updateDate: String = "",
    val useFixPrice: Boolean = false,
    val variantData: List<Any> = emptyList(),
    val variants: List<Any> = emptyList(),
    val variationGroups: List<Any> = emptyList(),
    val videos: List<Any> = emptyList()
)
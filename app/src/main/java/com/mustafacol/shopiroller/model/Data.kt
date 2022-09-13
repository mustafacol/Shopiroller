package com.mustafacol.shopiroller.model

data class Data(
    val categoryId: String,
    val createDate: String,
    val icon: String,
    val isActive: Boolean,
    val name: String,
    val orderIndex: Int,
    val subCategories: List<Any>,
    val totalProducts: Int,
    val updateDate: String
)
package com.mustafacol.shopiroller.model

data class Category(
    val categoryId: String = "",
    val createDate: String = "",
    val icon: Icon = Icon("", ""),
    val isActive: Boolean = false,
    val name: String = "",
    val orderIndex: Int = 0,
    val subCategories: List<Any> = emptyList(),
    val totalProducts: Int = 0,
    val updateDate: String = ""
)
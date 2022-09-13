package com.mustafacol.shopiroller.model

data class Source<T>(
    val data: T,
    val success: Boolean,
    val meta: Meta?
)
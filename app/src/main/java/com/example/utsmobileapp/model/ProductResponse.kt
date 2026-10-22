package com.example.utsmobileapp.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @field:SerializedName("products")
    val products: List<ProductItem?>? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("skip")
    val skip: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null
)

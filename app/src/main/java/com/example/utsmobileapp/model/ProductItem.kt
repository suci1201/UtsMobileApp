package com.example.utsmobileapp.model

import com.google.gson.annotations.SerializedName

data class ProductItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("stock")
    val stock: Int? = null,

    @field:SerializedName("availabilityStatus")
    val availabilityStatus: String? = null,

    @field:SerializedName("tags")
    val tags: List<String?>? = null,

    @field:SerializedName("dimensions")
    val dimensions: Dimensions? = null,

    @field:SerializedName("reviews")
    val reviews: List<ReviewsItem?>? = null,

    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("images")
    val images: List<String?>? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null
)

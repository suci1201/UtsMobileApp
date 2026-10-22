package com.example.utsmobileapp.model

import com.google.gson.annotations.SerializedName

data class ReviewsItem(

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("reviewerName")
    val reviewerName: String? = null,

    @field:SerializedName("reviewerEmail")
    val reviewerEmail: String? = null
)

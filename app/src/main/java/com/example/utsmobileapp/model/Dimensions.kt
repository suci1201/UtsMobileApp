package com.example.utsmobileapp.model

import com.google.gson.annotations.SerializedName

data class Dimensions(

    @field:SerializedName("width")
    val width: Double? = null,

    @field:SerializedName("height")
    val height: Double? = null,

    @field:SerializedName("depth")
    val depth: Double? = null
)

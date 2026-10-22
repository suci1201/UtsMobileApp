package com.example.utsmobileapp.retrofit

import com.example.utsmobileapp.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int,
        @Query("select") select: String
    ): Call<ProductResponse>
}

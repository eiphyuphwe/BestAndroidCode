package com.example.bestandroidcode.network

import com.example.bestandroidcode.model.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatAPI {
    @GET("/v1/images/search")
    fun getCatRandom(): Call<List<Cat>>

    @GET("/v1/images/search")
    fun getCatBasedOnCategory(@Query("category_ids") category_ids: String): Call<List<Cat>>
}
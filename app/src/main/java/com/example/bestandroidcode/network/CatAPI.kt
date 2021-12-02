package com.example.bestandroidcode.network

import com.example.bestandroidcode.model.Cat
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatAPI {


    @GET("/v1/images/search")
    suspend fun getCatRandom(): Response<List<Cat>>

    @GET("/v1/images/search")
    suspend fun getCatBasedOnCategory(@Query("category_ids") category_ids: String): Response<List<Cat>>
}
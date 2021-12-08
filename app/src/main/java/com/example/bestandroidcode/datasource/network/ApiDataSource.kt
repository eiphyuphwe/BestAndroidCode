package com.example.bestandroidcode.datasource.network

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import retrofit2.Response
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val catAPI: CatAPI) {

    suspend fun getCatRandom(): Response<List<Cat>> {
        return catAPI.getCatRandom()
    }

    suspend fun getCatBasedOnCategory(categoryIds: String): Response<List<Cat>> =
        catAPI.getCatBasedOnCategory(categoryIds)

}
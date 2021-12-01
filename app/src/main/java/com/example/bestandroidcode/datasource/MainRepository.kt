package com.example.bestandroidcode.datasource

import com.example.bestandroidcode.datasource.network.ApiDataSource
import com.example.bestandroidcode.helper.Resource
import com.example.bestandroidcode.model.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiDataSource: ApiDataSource)  {
    suspend fun getCatRandom() : Flow<Response<List<Cat>>> {
        return flow {
            emit(apiDataSource.getCatRandom())
        }.flowOn(Dispatchers.IO)
    }
}
package com.example.bestandroidcode.datasource.network

import com.example.bestandroidcode.datasource.MainRepository
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MainRepositoryTest {

    @Mock
    private lateinit var catAPI: CatAPI

    private lateinit var apiDataSource: ApiDataSource

    @Test
    fun testGetCatRandom_ReturnCatListSuccess() {
        apiDataSource = ApiDataSource(catAPI)
        var flow :Flow<Response<List<Cat>>>
        val catList : List<Cat> = listOf(
            Cat("1","https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg",300,300)
        )
        val response = Response.success(catList)

        runBlocking {
            Mockito.`when`(apiDataSource.getCatRandom()).thenReturn(response)
            val testee = MainRepository(apiDataSource)
            flow = testee.getCatRandom()
            Assert.assertNotNull(flow)
        }
    }

    @Test
    fun testGetCatByCategory_ReturnCatListSuccess() {
        apiDataSource = ApiDataSource(catAPI)
        var flow :Flow<Response<List<Cat>>>
        val catList : List<Cat> = listOf(
            Cat("1","https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg",300,300)
        )
        val response = Response.success(catList)

        runBlocking {
            Mockito.`when`(apiDataSource.getCatBasedOnCategory("1")).thenReturn(response)
            val testee = MainRepository(apiDataSource)
            flow = testee.getCatRandom()
            Assert.assertNotNull(flow)
            
        }
    }
}
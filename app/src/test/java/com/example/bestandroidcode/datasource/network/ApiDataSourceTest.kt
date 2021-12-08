package com.example.bestandroidcode.datasource.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import com.example.currencyconverterapp.viewmodel.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ApiDataSourceTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var catAPI: CatAPI

    lateinit var apiDataSource: ApiDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiDataSource = ApiDataSource(catAPI)
    }

    @Test
    fun testGetCatRandom_ReturnCatListSuccess() {
        val catList: List<Cat> = listOf(
            Cat("1", "https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg", 300, 300)
        )
        val response = Response.success(catList)
        runBlocking {
            Mockito.`when`(catAPI.getCatRandom()).thenReturn(response)
            val actualResponse = apiDataSource.getCatRandom()
            Assert.assertEquals(1, actualResponse.body()!!.size)
            Assert.assertEquals("1", actualResponse.body()!!.get(0).id)
        }
    }

    @Test
    fun testGetCatBasedOnCategory_ReturnCatListSuccess() {
        val catList: List<Cat> = listOf(
            Cat("1", "https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg", 300, 300)
        )
        val response = Response.success(catList)
        runBlocking {
            Mockito.`when`(catAPI.getCatBasedOnCategory("1")).thenReturn(response)
            val actualResponse = apiDataSource.getCatBasedOnCategory("1")
            Assert.assertEquals(1, actualResponse.body()!!.size)
            Assert.assertEquals("1", actualResponse.body()!!.get(0).id)
        }
    }
}
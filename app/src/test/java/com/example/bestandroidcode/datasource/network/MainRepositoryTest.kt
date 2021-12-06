package com.example.bestandroidcode.datasource.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bestandroidcode.datasource.MainRepository
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import com.example.currencyconverterapp.viewmodel.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
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
class MainRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var catAPI: CatAPI

    @Mock
    private lateinit var apiDataSource: ApiDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetCatRandom_ReturnCatListSuccess() {

        var flow :Flow<Response<List<Cat>>>
        val catList : List<Cat> = listOf(
            Cat("1","https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg",300,300)
        )
        val response = Response.success(catList)

        runBlockingTest {
            Mockito.`when`(apiDataSource.getCatRandom()).thenReturn(response)
            val testee = MainRepository(apiDataSource)
            flow = testee.getCatRandom()
            Assert.assertNotNull(flow)
            flow.collect {
                result:Response<List<Cat>> ->
                Assert.assertEquals(1,result.body()!!.size)
                Assert.assertEquals("https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg",result.body()!!.get(0).url)
            }
        }
    }

    @Test
    fun testGetCatByCategory_ReturnCatListSuccess() {
        var flow :Flow<Response<List<Cat>>>
        val catList : List<Cat> = listOf(
            Cat("1","https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg",300,300)
        )
        val response = Response.success(catList)

        runBlockingTest {
            Mockito.`when`(apiDataSource.getCatBasedOnCategory("1")).thenReturn(response)
            val testee = MainRepository(apiDataSource)
            flow = testee.getCatBasedOnCategory("1")
            Assert.assertNotNull(flow)
            flow.collect {
                    result:Response<List<Cat>> ->
                Assert.assertEquals(1,result.body()!!.size)
                Assert.assertEquals("https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg",result.body()!!.get(0).url)
            }
        }
    }
}
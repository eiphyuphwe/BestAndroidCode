package com.example.bestandroidcode.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.bestandroidcode.datasource.MainRepository
import com.example.bestandroidcode.datasource.network.ApiDataSource
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatAPI
import com.example.currencyconverterapp.viewmodel.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var catAPI: CatAPI

    @Mock
    lateinit var apiDataSource: ApiDataSource

    @Mock
    lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun testGetCatRandom_fetchCatListSuccess() {
        val expectedCat = Cat("1", "https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg", 300, 300)
        val catList: List<Cat> = listOf(
            Cat("1", "https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg", 300, 300)
        )
        val response = Response.success(catList)
        testCoroutineRule.runBlockingTest {
            val mainViewModel = MainViewModel(mainRepository)
            val channel = Channel<Response<List<Cat>>>()
            val flow = channel.consumeAsFlow()
            Mockito.`when`(mainRepository.getCatRandom()).thenReturn(flow)
            launch {
                channel.send(response)
            }
            mainViewModel.getCatRandom()
            val actualResponse = mainViewModel.randomCatDataList.blockingObserve()
            Assert.assertNotNull(actualResponse)
            Assert.assertNotNull(actualResponse?.body())
            Assert.assertEquals(1, actualResponse?.body()!!.size)
        }
    }

    @Test
    fun testgetCatBasedOnCategory_fetchCatListByCategorySuccess() {
        val expectedCat = Cat("1", "https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg", 300, 300)
        val catList: List<Cat> = listOf(
            Cat("1", "https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg", 300, 300)
        )
        val response = Response.success(catList)
        testCoroutineRule.runBlockingTest {
            val mainViewModel = MainViewModel(mainRepository)
            val channel = Channel<Response<List<Cat>>>()
            val flow = channel.consumeAsFlow()
            Mockito.`when`(mainRepository.getCatBasedOnCategory("1")).thenReturn(flow)
            launch {
                channel.send(response)
            }
            mainViewModel.getCatBasedOnCategory("1")
            val actualResponse = mainViewModel.randomCatDataList.blockingObserve()
            Assert.assertNotNull(actualResponse)
            Assert.assertNotNull(actualResponse?.body())
            Assert.assertEquals(1, actualResponse?.body()!!.size)
        }
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

}





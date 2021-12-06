package com.example.bestandroidcode.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class CatAPITest {

   /* @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()*/
    private val server = MockWebServer()
    lateinit var catAPIService: CatAPI
    private lateinit var mockedResponse: String
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    @Before
    fun init() {
        server.start(8000)
        var BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()

         catAPIService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(CatAPI::class.java)
    }

   @Test
    fun testCatRandomApi_Success() {
        mockedResponse = "[{\"breeds\":[],\"id\":\"SqEbHe6XM\",\"url\":\"https://cdn2.thecatapi.com/images/SqEbHe6XM.jpg\",\"width\":600,\"height\":400}]"
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { catAPIService.getCatRandom() }
        Assert.assertNotNull(response)
        Assert.assertEquals(1,response.body()!!.size)
        Assert.assertEquals("SqEbHe6XM",response.body()!!.get(0).id)
    }

    @Test
    fun testCatBaseCategoryApi_Success() {
        mockedResponse = "[{\"breeds\":[],\"categories\":[{\"id\":5,\"name\":\"boxes\"}],\"id\":\"5kr\",\"url\":\"https://cdn2.thecatapi.com/images/5kr.jpg\",\"width\":479,\"height\":364}]"
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { catAPIService.getCatBasedOnCategory("5") }
        Assert.assertNotNull(response)
        Assert.assertEquals(1,response.body()!!.size)
        Assert.assertEquals("5kr",response.body()!!.get(0).id)
    }



    @After
    fun tearDown() {
        server.shutdown()
    }



}
package com.example.bestandroidcode.di


import com.example.bestandroidcode.BuildConfig
import com.example.bestandroidcode.datasource.network.ApiDataSource
import com.example.bestandroidcode.network.CatAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //provides Gson
    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    //Provides for networking
    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/")
        .client(OkHttpClient.Builder().also {
                client -> if(BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
            client.connectTimeout(120, TimeUnit.SECONDS)
            client.readTimeout(120, TimeUnit.SECONDS)
            client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
            }
        }.build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    //Api Service with retrofit instance
    @Provides
    @Singleton
    fun provideBuildService(retrofit: Retrofit) : CatAPI = retrofit.create(CatAPI::class.java)

    @Provides
    @Singleton
    fun provideApiDataSource(catAPIService: CatAPI) = ApiDataSource(catAPIService)

}
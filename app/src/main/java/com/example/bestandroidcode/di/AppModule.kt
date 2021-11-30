package com.example.bestandroidcode.di

import com.example.bestandroidcode.network.CatAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    //provides Gson
    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    //Provides for networking
    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/")
        .client(
            OkHttpClient.Builder().build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    //Api Service with retrofit instance
    @Provides
    @Singleton
    fun provideBuildService(retrofit: Retrofit) : CatAPI = retrofit.create(CatAPI::class.java)
}
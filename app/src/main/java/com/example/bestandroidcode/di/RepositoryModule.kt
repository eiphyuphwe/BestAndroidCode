package com.example.bestandroidcode.di

import com.example.bestandroidcode.datasource.network.ApiDataSource
import com.example.bestandroidcode.datasource.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesMainRepo(apiDataSource: ApiDataSource) : MainRepository = MainRepository(apiDataSource)
}
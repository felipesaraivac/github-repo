package com.saraiva.github.core.di

import com.google.gson.GsonBuilder
import com.saraiva.github.core.Constants
import com.saraiva.github.core.network.GHService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.saraiva.github.data.RepositoryImpl
import com.saraiva.github.domain.repository.Repository

@Module
@InstallIn(SingletonComponent::class)
object DI {

    @Singleton
    @Provides
    fun provideService(): GHService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit.create(GHService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(service: GHService): Repository = RepositoryImpl(service)

}
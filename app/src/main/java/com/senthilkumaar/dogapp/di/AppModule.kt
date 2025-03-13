package com.senthilkumaar.dogapp.di

import com.senthilkumaar.dogapp.data.DogApiService
import com.senthilkumaar.dogapp.repository.DogRepository
import com.senthilkumaar.dogapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

    @Provides
    @Singleton
    fun provideDogApiService(retrofit: Retrofit): DogApiService =
        retrofit.create(DogApiService::class.java)

    @Provides
    @Singleton
    fun provideDogRepository(apiService: DogApiService): DogRepository =
        DogRepository(apiService)
}
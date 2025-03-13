package com.senthilkumaar.dogapp.data

import com.senthilkumaar.dogapp.model.Dog
import retrofit2.http.GET

interface DogApiService {
    @GET("v1/breeds")
    suspend fun getBreeds(): List<Dog>
}
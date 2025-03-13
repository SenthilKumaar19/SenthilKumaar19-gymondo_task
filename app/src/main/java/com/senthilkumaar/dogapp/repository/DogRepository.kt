package com.senthilkumaar.dogapp.repository

import com.senthilkumaar.dogapp.data.DogApiService
import com.senthilkumaar.dogapp.model.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DogRepository @Inject constructor(private val apiService: DogApiService) {
    fun getBreeds(): Flow<List<Dog>> = flow { emit(apiService.getBreeds()) }
}
package com.example.cuidapet.network
import com.example.cuidapet.model.DogBreed
import retrofit2.http.GET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DogApiService {
    @GET("breeds")
    suspend fun getBreeds(): List<DogBreed>
}
package com.example.cuidapet.network

import retrofit2.Response
import retrofit2.http.GET

data class DogBreedResponse(
    val message: Map<String, List<String>>,
    val status: String
)

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<DogBreedResponse>
}

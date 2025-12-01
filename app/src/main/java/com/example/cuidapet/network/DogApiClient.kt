package com.example.cuidapet.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogApiClient {
    private const val BASE_URL = "https://api.thedogapi.com/v1/"

    val api: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }
}

package com.example.cuidapet.network

import com.example.cuidapet.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)

interface UserApiService {
    @POST("api/users/register")
    fun register(@Body user: Usuario): Call<Usuario>

    @POST("api/users/login")
    fun login(@Body loginRequest: LoginRequest): Call<Usuario>

    @GET("api/users")
    fun getAllUsers(): Call<List<Usuario>>
}

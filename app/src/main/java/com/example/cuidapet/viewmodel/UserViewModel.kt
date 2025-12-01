package com.example.cuidapet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cuidapet.model.Usuario
import com.example.cuidapet.network.UserApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val apiService: UserApiService) : ViewModel() {

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> = _usuarios

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchUsuarios() {
        val call = apiService.getAllUsers()
        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    _usuarios.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "Error al obtener usuarios: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                _error.value = "Fallo de conexión: ${t.message}"
            }
        })
    }

    fun registrarUsuario(usuario: Usuario) {
        val call = apiService.register(usuario)
        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    _error.value = "Usuario registrado correctamente: ${response.body()?.nombre}"
                } else {
                    _error.value = "Error al registrar: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                _error.value = "Fallo de conexión: ${t.message}"
            }
        })
    }
}

class UserViewModelFactory(private val apiService: UserApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(apiService) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}

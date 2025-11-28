package com.example.cuidapet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuidapet.network.RetrofitClient
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> = _breeds

    // --- INICIO DE LA MODIFICACIÓN ---
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    // --- FIN DE LA MODIFICACIÓN ---

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getBreeds()
                if (response.isSuccessful) {
                    val breedMap = response.body()?.message ?: emptyMap()
                    val breedList = breedMap.flatMap { (breed, subBreeds) ->
                        if (subBreeds.isEmpty()) listOf(breed)
                        else subBreeds.map { "$breed $it" }
                    }
                    _breeds.value = breedList
                    _error.value = null // Limpiar error si la llamada fue exitosa
                } else {
                    // --- INICIO DE LA MODIFICACIÓN ---
                    _error.value = "Error al obtener las razas: ${response.code()}"
                    // --- FIN DE LA MODIFICACIÓN ---
                }
            } catch (e: Exception) {
                // --- INICIO DE LA MODIFICACIÓN ---
                _error.value = "Excepción: ${e.message}"
                e.printStackTrace()
                // --- FIN DE LA MODIFICACIÓN ---
            }
        }
    }
}

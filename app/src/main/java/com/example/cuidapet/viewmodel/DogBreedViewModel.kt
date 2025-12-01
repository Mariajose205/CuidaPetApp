package com.example.cuidapet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuidapet.model.DogBreed
import com.example.cuidapet.network.DogApiClient
import kotlinx.coroutines.launch

class DogBreedViewModel : ViewModel() {

    private val _razas = MutableLiveData<List<DogBreed>>()
    val razas: LiveData<List<DogBreed>> get() = _razas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val lista = DogApiClient.api.getBreeds()
                _razas.value = lista
            } catch (e: Exception) {
                _error.value = "Error al obtener razas: ${e.message}"
            }
        }
    }
}

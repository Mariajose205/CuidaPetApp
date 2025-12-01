package com.example.cuidapet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cuidapet.data.MascotaDao
import com.example.cuidapet.model.Mascota

class MascotaViewModel(private val mascotaDao: MascotaDao) : ViewModel() {

    fun obtenerMascotas(usuarioId: Int): LiveData<List<Mascota>> {
        return mascotaDao.obtenerMascotasPorUsuario(usuarioId)
    }
}

// Fábrica para crear el ViewModel con su dependencia (el DAO)
class MascotaViewModelFactory(private val mascotaDao: MascotaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MascotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MascotaViewModel(mascotaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

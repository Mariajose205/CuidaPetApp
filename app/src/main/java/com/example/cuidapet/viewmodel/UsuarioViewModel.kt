package com.example.cuidapet.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cuidapet.data.UsuarioDao
import com.example.cuidapet.model.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel(private val usuarioDao: UsuarioDao) : ViewModel() {

    fun registrarUsuario(
        nombre: String,
        fechaNacimiento: String,
        correo: String,
        contrasena: String
    ) {
        viewModelScope.launch {
            val nuevoUsuario = Usuario(nombre, fechaNacimiento, correo, contrasena)
            usuarioDao.registrar(nuevoUsuario)
        }
    }

    fun iniciarSesion(correo: String, contrasena: String, onResult: (Usuario?) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioDao.iniciarSesion(correo, contrasena)
            onResult(usuario)
        }
    }

    fun actualizarFotoUsuario(idUsuario: Int, fotoUri: Uri) {
        viewModelScope.launch {
            usuarioDao.actualizarFoto(idUsuario, fotoUri.toString())
        }
    }

    fun obtenerUsuarioPorId(idUsuario: Int, onResult: (Usuario?) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioDao.obtenerUsuarioPorId(idUsuario)
            onResult(usuario)
        }
    }
}

// Fábrica para crear el ViewModel con su dependencia (el DAO)
class UsuarioViewModelFactory(private val usuarioDao: UsuarioDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(usuarioDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

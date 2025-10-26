package com.example.cuidapet.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuidapet.data.CuidaPetDataBase
import com.example.cuidapet.model.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = CuidaPetDataBase.getDatabase(application).usuarioDao()

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
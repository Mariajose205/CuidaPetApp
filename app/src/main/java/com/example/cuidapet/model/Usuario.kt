package com.example.cuidapet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    val nombre: String,
    val fechaNacimiento: String,
    val correo: String,
    val contrasena: String,
    var fotoUri: String? = null, // <-- CAMPO AÑADIDO
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

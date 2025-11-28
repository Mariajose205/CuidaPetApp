package com.example.cuidapet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val edad: String,
    val raza: String,
    val peso: String,
    val fotoUri: String? = null,
    val idUsuario: Int
)

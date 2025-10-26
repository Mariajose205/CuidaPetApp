package com.example.cuidapet.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cuidapet.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun registrar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun iniciarSesion(correo: String, contrasena: String): Usuario?

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun obtenerUsuarioPorId(id: Int): Usuario?

    @Query("UPDATE usuarios SET fotoUri = :uri WHERE id = :id")
    suspend fun actualizarFoto(id: Int, uri: String)
}

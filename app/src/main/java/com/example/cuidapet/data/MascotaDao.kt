package com.example.cuidapet.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cuidapet.model.Mascota

@Dao
interface MascotaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(mascota: Mascota)

    @Query("SELECT * FROM mascotas WHERE idUsuario = :idUsuario")
    fun obtenerMascotasPorUsuario(idUsuario: Int): LiveData<List<Mascota>>
}

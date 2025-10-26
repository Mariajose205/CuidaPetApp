package com.example.cuidapet.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.cuidapet.model.Mascota

@Dao
interface MascotaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(mascota: Mascota)
}

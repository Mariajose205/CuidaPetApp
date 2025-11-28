package com.example.cuidapet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cuidapet.model.Mascota
import com.example.cuidapet.model.Usuario

@Database(entities = [Usuario::class, Mascota::class], version = 5) // <-- VERSIÓN INCREMENTADA
abstract class CuidaPetDataBase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun mascotaDao(): MascotaDao

    companion object {
        @Volatile
        private var INSTANCE: CuidaPetDataBase? = null

        fun getDatabase(context: Context): CuidaPetDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CuidaPetDataBase::class.java,
                    "cuida_pet_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

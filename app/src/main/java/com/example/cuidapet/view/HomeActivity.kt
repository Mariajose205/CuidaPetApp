package com.example.cuidapet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.cuidapet.MainActivity
import com.example.cuidapet.R

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnRegistrarMascota = findViewById<Button>(R.id.btnRegistrarMascota)
        val btnTuMascota = findViewById<Button>(R.id.btnTuMascota)
        val btnRecomendaciones = findViewById<Button>(R.id.btnRecomendaciones)
        val btnPerfil = findViewById<Button>(R.id.btnPerfil)
        val btnVerRazas = findViewById<Button>(R.id.btnVerRazas)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        btnRegistrarMascota.setOnClickListener {
            val intent = Intent(this, RegistrarMascotaActivity::class.java)
            startActivity(intent)
        }

        btnTuMascota.setOnClickListener {
            val intent = Intent(this, VerMascotaActivity::class.java)
            startActivity(intent)
        }

        btnRecomendaciones.setOnClickListener {
            val intent = Intent(this, RecomendacionesActivity::class.java)
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        btnVerRazas.setOnClickListener {
            val intent = Intent(this, DogBreedActivity::class.java)
            startActivity(intent)
        }

        btnCerrarSesion.setOnClickListener {
            val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                clear()
                apply()
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}

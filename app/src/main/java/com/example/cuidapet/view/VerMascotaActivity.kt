package com.example.cuidapet.view

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.cuidapet.R

class VerMascotaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_mascota)

        val nombre = intent.getStringExtra("nombre")
        val edad = intent.getStringExtra("edad")
        val raza = intent.getStringExtra("raza")
        val peso = intent.getStringExtra("peso")
        val fotoUri = intent.getStringExtra("fotoUri")

        val tvNombre = findViewById<TextView>(R.id.tvNombreMascota)
        val tvEdad = findViewById<TextView>(R.id.tvEdadMascota)
        val tvRaza = findViewById<TextView>(R.id.tvRazaMascota)
        val tvPeso = findViewById<TextView>(R.id.tvPesoMascota)
        val imgMascota = findViewById<ImageView>(R.id.imgMascotaVista)
        val btnVolver = findViewById<Button>(R.id.btnVolverHome)

        tvNombre.text = "🐶 Nombre: $nombre"
        tvEdad.text = "📅 Edad: $edad"
        tvRaza.text = "🐾 Raza: $raza"
        tvPeso.text = "⚖️ Peso: $peso"

        fotoUri?.let {
            if (it.isNotEmpty()) {
                imgMascota.setImageURI(Uri.parse(it))
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}

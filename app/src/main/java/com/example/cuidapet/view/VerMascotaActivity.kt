package com.example.cuidapet.view

import android.content.Intent
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
        val fecha = intent.getStringExtra("fecha")
        val raza = intent.getStringExtra("raza")
        val fotoUri = intent.getStringExtra("fotoUri")

        val tvNombre = findViewById<TextView>(R.id.tvNombreMascota)
        val tvFecha = findViewById<TextView>(R.id.tvFechaMascota)
        val tvRaza = findViewById<TextView>(R.id.tvRazaMascota)
        val imgMascota = findViewById<ImageView>(R.id.imgMascotaVista)
        val btnVolverHome = findViewById<Button>(R.id.btnVolverHome)

        tvNombre.text = "🐶 Nombre: $nombre"
        tvFecha.text = "📅 Nacimiento: $fecha"
        tvRaza.text = "🐾 Raza: $raza"

        fotoUri?.let {
            imgMascota.setImageURI(Uri.parse(it))
        }

        btnVolverHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

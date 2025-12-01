package com.example.cuidapet.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R
import com.example.cuidapet.data.CuidaPetDataBase
import com.example.cuidapet.viewmodel.MascotaViewModel
import com.example.cuidapet.viewmodel.MascotaViewModelFactory

class VerMascotaActivity : ComponentActivity() {

    private lateinit var viewModel: MascotaViewModel
    private lateinit var mascotaAdapter: MascotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_mascota)

        // --- 1. Configurar RecyclerView ---
        val rvMascotas = findViewById<RecyclerView>(R.id.rvMascotas)
        mascotaAdapter = MascotaAdapter()
        rvMascotas.adapter = mascotaAdapter
        rvMascotas.layoutManager = LinearLayoutManager(this)

        // --- 2. Inicializar ViewModel con la Fábrica ---
        val database = CuidaPetDataBase.getDatabase(application)
        val factory = MascotaViewModelFactory(database.mascotaDao())
        viewModel = ViewModelProvider(this, factory).get(MascotaViewModel::class.java)

        // --- 3. Obtener ID de Usuario ---
        val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
        val usuarioId = sharedPref.getInt("ID_USUARIO", -1)

        if (usuarioId != -1) {
            // --- 4. Observar la lista de mascotas ---
            viewModel.obtenerMascotas(usuarioId).observe(this) { mascotas ->
                if (mascotas.isNotEmpty()) {
                    mascotaAdapter.submitList(mascotas)
                } else {
                    Toast.makeText(this, "Aún no has registrado ninguna mascota", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_LONG).show()
            finish()
        }

        // --- 5. Botón Volver ---
        val btnVolver = findViewById<Button>(R.id.btnVolverHome)
        btnVolver.setOnClickListener {
            finish()
        }
    }
}

package com.example.cuidapet.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R
import com.example.cuidapet.viewmodel.DogViewModel

class DogBreedsActivity : ComponentActivity() { // <-- CAMBIO AQUÍ

    private lateinit var viewModel: DogViewModel
    private lateinit var adapter: BreedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breeds)

        // Inicializar el adapter
        adapter = BreedAdapter()

        // Configurar el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBreeds)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this)[DogViewModel::class.java]

        // Observar los datos
        viewModel.breeds.observe(this) { breedList ->
            adapter.submitList(breedList)
        }

        // Observar los errores
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Llamar a la API
        viewModel.fetchBreeds()
    }
}

package com.example.cuidapet.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R
import com.example.cuidapet.viewmodel.DogBreedViewModel

class DogBreedActivity : ComponentActivity() {

    private val viewModel: DogBreedViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DogBreedAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breeds)

        recyclerView = findViewById(R.id.recyclerViewBreeds)
        progressBar = findViewById(R.id.progressBarBreeds)


        adapter = DogBreedAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observa razas
        viewModel.razas.observe(this) { lista ->
            progressBar.visibility = View.GONE
            adapter.submitList(lista)
        }

        // Observa errores
        viewModel.error.observe(this) { mensaje ->
            progressBar.visibility = View.GONE
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }

        // Mostrar progreso y cargar razas
        progressBar.visibility = View.VISIBLE
        viewModel.fetchBreeds()
    }
}
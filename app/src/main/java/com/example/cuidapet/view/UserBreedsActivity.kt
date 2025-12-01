package com.example.cuidapet.view

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R
import com.example.cuidapet.model.Usuario
import com.example.cuidapet.network.RetrofitClient
import com.example.cuidapet.viewmodel.UserViewModel
import com.example.cuidapet.viewmodel.UserViewModelFactory

class UserBreedsActivity : ComponentActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // Inicializar el adapter
        adapter = UsuarioAdapter()

        // Configurar el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Inicializar el ViewModel con la Fábrica
        val factory = UserViewModelFactory(apiService = RetrofitClient.api)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Observar los datos
        viewModel.usuarios.observe(this) { listaUsuarios ->
            adapter.submitList(listaUsuarios)
        }

        // Observar los errores
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Llamar a la API para listar usuarios
        viewModel.fetchUsuarios()

        // Botón para registrar un usuario de prueba
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            val nuevoUsuario = Usuario(
                nombre = "Pedro",
                fechaNacimiento = "1990-05-15", // formato ISO recomendado
                correo = "pedro@gmail.com",
                contrasena = "123456"
            )
            viewModel.registrarUsuario(nuevoUsuario)
        }
    }
}
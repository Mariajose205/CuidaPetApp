package com.example.cuidapet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cuidapet.R
import com.example.cuidapet.data.CuidaPetDataBase
import com.example.cuidapet.viewmodel.UsuarioViewModel
import com.example.cuidapet.viewmodel.UsuarioViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val database = CuidaPetDataBase.getDatabase(application)
        val factory = UsuarioViewModelFactory(database.usuarioDao())
        viewModel = ViewModelProvider(this, factory).get(UsuarioViewModel::class.java)

        val etCorreo = findViewById<TextInputEditText>(R.id.etCorreo)
        val etContrasena = findViewById<TextInputEditText>(R.id.etContrasena)
        
        val btnIniciarSesion = findViewById<Button>(R.id.btnLogin)
        val btnIrARegistro = findViewById<Button>(R.id.btnIrRegistro)

        btnIniciarSesion.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.iniciarSesion(correo, contrasena) { usuario ->
                if (usuario != null) {
                    // --- INICIO DE LA MODIFICACIÓN ---
                    val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("ID_USUARIO", usuario.id)
                        putString("NOMBRE_USUARIO", usuario.nombre)
                        putString("CORREO_USUARIO", usuario.correo)
                        putString("FECHA_NACIMIENTO_USUARIO", usuario.fechaNacimiento)
                        apply()
                    }
                    // --- FIN DE LA MODIFICACIÓN ---

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnIrARegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}

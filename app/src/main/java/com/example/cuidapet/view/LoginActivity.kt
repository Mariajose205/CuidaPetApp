package com.example.cuidapet.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cuidapet.R
import com.example.cuidapet.viewmodel.UsuarioViewModel

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnIrRegistro = findViewById<Button>(R.id.btnIrRegistro)

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.iniciarSesion(correo, contrasena) { usuario ->
                    runOnUiThread {
                        if (usuario != null) {
                            val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putInt("ID_USUARIO", usuario.id)
                                putString("NOMBRE_USUARIO", usuario.nombre)
                                putString("CORREO_USUARIO", usuario.correo)
                                putString("FECHA_NACIMIENTO_USUARIO", usuario.fechaNacimiento)

                                apply()
                            }

                            Toast.makeText(this, "¡Bienvenido, ${usuario.nombre}!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        btnIrRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}

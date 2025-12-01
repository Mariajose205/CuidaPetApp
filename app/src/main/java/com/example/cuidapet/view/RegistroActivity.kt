package com.example.cuidapet.view

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
import com.google.android.material.textfield.TextInputLayout

class RegistroActivity : ComponentActivity() {

    private lateinit var viewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // --- INICIO DE LA MODIFICACIÓN ---
        val database = CuidaPetDataBase.getDatabase(application)
        val factory = UsuarioViewModelFactory(database.usuarioDao())
        viewModel = ViewModelProvider(this, factory).get(UsuarioViewModel::class.java)
        // --- FIN DE LA MODIFICACIÓN ---

        val tilNombre = findViewById<TextInputLayout>(R.id.tilNombre)
        val tilFechaNacimiento = findViewById<TextInputLayout>(R.id.tilFechaNacimiento)
        val tilCorreo = findViewById<TextInputLayout>(R.id.tilCorreo)
        val tilContrasena = findViewById<TextInputLayout>(R.id.tilContrasena)

        val etNombre = findViewById<TextInputEditText>(R.id.etNombre)
        val etFechaNacimiento = findViewById<TextInputEditText>(R.id.etFechaNacimiento)
        val etCorreo = findViewById<TextInputEditText>(R.id.etCorreoRegistro)
        val etContrasena = findViewById<TextInputEditText>(R.id.etContrasenaRegistro)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnVolverLogin = findViewById<Button>(R.id.btnVolverLogin)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val fecha = etFechaNacimiento.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            var esValido = true

            if (nombre.isEmpty()) {
                tilNombre.error = "Este campo es obligatorio"
                esValido = false
            } else {
                tilNombre.error = null
            }

            if (fecha.isEmpty()) {
                tilFechaNacimiento.error = "Este campo es obligatorio"
                esValido = false
            } else if (!fecha.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
                tilFechaNacimiento.error = "Formato inválido (dd/MM/yyyy)"
                esValido = false
            } else {
                tilFechaNacimiento.error = null
            }

            if (correo.isEmpty()) {
                tilCorreo.error = "Este campo es obligatorio"
                esValido = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                tilCorreo.error = "Correo inválido"
                esValido = false
            } else {
                tilCorreo.error = null
            }

            if (contrasena.isEmpty()) {
                tilContrasena.error = "Este campo es obligatorio"
                esValido = false
            } else if (contrasena.length < 6) {
                tilContrasena.error = "Mínimo 6 caracteres"
                esValido = false
            } else {
                tilContrasena.error = null
            }

            if (esValido) {
                viewModel.registrarUsuario(nombre, fecha, correo, contrasena)
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnVolverLogin.setOnClickListener {
            finish()
        }
    }
}

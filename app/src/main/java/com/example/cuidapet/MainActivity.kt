package com.example.cuidapet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.cuidapet.view.HomeActivity
import com.example.cuidapet.view.LoginActivity
import com.example.cuidapet.view.RegistroActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- INICIO DE LA MODIFICACIÓN ---
        // Comprobar si ya hay una sesión activa
        val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
        val usuarioId = sharedPref.getInt("ID_USUARIO", -1) // -1 es el valor por defecto si no se encuentra

        if (usuarioId != -1) {
            // Si hay un usuario, ir directamente a Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Cierra MainActivity para que no se pueda volver atrás
            return // Detiene la ejecución del resto del onCreate
        }
        // --- FIN DE LA MODIFICACIÓN ---

        // Si no hay sesión, mostrar la pantalla de bienvenida
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}

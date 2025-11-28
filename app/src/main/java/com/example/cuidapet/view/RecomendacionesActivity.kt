package com.example.cuidapet.view


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.cuidapet.R

class RecomendacionesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val btnVolverHome = findViewById<Button>(R.id.btnVolverHome)
        btnVolverHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

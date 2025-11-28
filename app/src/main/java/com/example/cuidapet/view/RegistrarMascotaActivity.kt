package com.example.cuidapet.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.cuidapet.R
import com.example.cuidapet.data.CuidaPetDataBase
import com.example.cuidapet.model.Mascota
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RegistrarMascotaActivity : ComponentActivity() {

    private lateinit var imgMascota: ImageView
    private var fotoUri: Uri? = null
    private var idUsuario: Int = -1

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) tomarFoto() else Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
    }

    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            fotoUri = saveBitmapToInternalStorage(it)
            imgMascota.setImageURI(fotoUri)
        }
    }

    private val selectImage = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            try {
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                fotoUri = it
                imgMascota.setImageURI(it)
            } catch (e: SecurityException) {
                Log.e("RegistrarMascota", "No se pudo obtener permiso para la URI.", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_mascota)

        val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
        idUsuario = sharedPref.getInt("ID_USUARIO", -1)

        imgMascota = findViewById(R.id.imgMascota)
        val etNombreMascota = findViewById<EditText>(R.id.etNombreMascota)
        val etEdadMascota = findViewById<EditText>(R.id.etEdadMascota)
        val etRazaMascota = findViewById<EditText>(R.id.etRazaMascota)
        val etPesoMascota = findViewById<EditText>(R.id.etPesoMascota)
        val btnSubirFoto = findViewById<Button>(R.id.btnSubirFotoMascota)
        val btnGuardarMascota = findViewById<Button>(R.id.btnGuardarMascota)

        btnSubirFoto.setOnClickListener { mostrarDialogoSeleccion() }

        btnGuardarMascota.setOnClickListener {
            val nombre = etNombreMascota.text.toString().trim()
            val edad = etEdadMascota.text.toString().trim()
            val raza = etRazaMascota.text.toString().trim()
            val peso = etPesoMascota.text.toString().trim()

            if (nombre.isEmpty() || edad.isEmpty() || raza.isEmpty() || peso.isEmpty()) {
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            } else if (idUsuario == -1) {
                Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show()
            } else {
                val mascota = Mascota(
                    nombre = nombre,
                    edad = edad,
                    raza = raza,
                    peso = peso,
                    fotoUri = fotoUri?.toString(),
                    idUsuario = idUsuario
                )

                lifecycleScope.launch {
                    val mascotaDao = CuidaPetDataBase.getDatabase(applicationContext).mascotaDao()
                    mascotaDao.insertar(mascota)
                    Toast.makeText(this@RegistrarMascotaActivity, "¡Mascota registrada con éxito!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun mostrarDialogoSeleccion() {
        AlertDialog.Builder(this)
            .setTitle("Foto de la mascota")
            .setItems(arrayOf("Elegir de la Galería", "Tomar foto con la cámara")) { _, which ->
                when (which) {
                    0 -> selectImage.launch(arrayOf("image/*"))
                    1 -> chequearPermisoCamara()
                }
            }
            .show()
    }

    private fun chequearPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            tomarFoto()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun tomarFoto() {
        takePicturePreview.launch(null)
    }

    private fun saveBitmapToInternalStorage(bitmap: Bitmap): Uri? {
        return try {
            val imagesDir = File(filesDir, "pet_images")
            if (!imagesDir.exists()) imagesDir.mkdir()
            val file = File(imagesDir, "mascota_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            }
            Uri.fromFile(file)
        } catch (e: IOException) {
            Log.e("RegistrarMascota", "Error al guardar bitmap", e)
            null
        }
    }
}

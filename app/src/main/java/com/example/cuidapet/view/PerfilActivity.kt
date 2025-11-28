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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cuidapet.R
import com.example.cuidapet.viewmodel.UsuarioViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PerfilActivity : ComponentActivity() {

    private lateinit var imgPerfil: ImageView
    private lateinit var viewModel: UsuarioViewModel
    private var idUsuario: Int = -1

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) tomarFoto() else Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
    }

    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            val fotoUri = saveBitmapToInternalStorage(it)
            if (fotoUri != null) {
                actualizarFotoPerfil(fotoUri)
                Toast.makeText(this, "¡Foto de perfil actualizada!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar la foto.", Toast.LENGTH_SHORT).show()
            }
        } ?: Toast.makeText(this, "No se tomó ninguna foto.", Toast.LENGTH_SHORT).show()
    }

    private val selectImage = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            try {
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                actualizarFotoPerfil(it)
            } catch (e: SecurityException) {
                Log.e("PerfilActivity", "No se pudo obtener permiso para la URI.", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        viewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]
        imgPerfil = findViewById(R.id.imgPerfil)

        findViewById<Button>(R.id.btnCambiarFoto).setOnClickListener { mostrarDialogoSeleccion() }
        findViewById<Button>(R.id.btnVolverHome).setOnClickListener { finish() }

        cargarDatosDeUsuario()
    }

    private fun cargarDatosDeUsuario() {
        val sharedPref = getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE)
        idUsuario = sharedPref.getInt("ID_USUARIO", -1)

        findViewById<TextView>(R.id.tvNombre).text = "Nombre: ${sharedPref.getString("NOMBRE_USUARIO", "...")}"
        findViewById<TextView>(R.id.tvCorreo).text = "Correo: ${sharedPref.getString("CORREO_USUARIO", "...")}"
        findViewById<TextView>(R.id.tvFechaNacimiento).text = "📅 Nacimiento: ${sharedPref.getString("FECHA_NACIMIENTO_USUARIO", "...")}"

        viewModel.obtenerUsuarioPorId(idUsuario) { usuario ->
            usuario?.fotoUri?.let {
                imgPerfil.setImageURI(Uri.parse(it))
            }
        }
    }

    private fun actualizarFotoPerfil(uri: Uri) {
        imgPerfil.setImageURI(uri)
        viewModel.actualizarFotoUsuario(idUsuario, uri)
        actualizarFotoEnSesion(uri)
    }

    private fun mostrarDialogoSeleccion() {
        AlertDialog.Builder(this)
            .setTitle("Cambiar foto de perfil")
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
            val imagesDir = File(filesDir, "images")
            if (!imagesDir.exists()) imagesDir.mkdir()
            val file = File(imagesDir, "profile_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            }
            Uri.fromFile(file)
        } catch (e: IOException) {
            Log.e("PerfilActivity", "Error al guardar bitmap", e)
            null
        }
    }

    private fun actualizarFotoEnSesion(uri: Uri) {
        getSharedPreferences("CuidaPetPrefs", Context.MODE_PRIVATE).edit()
            .putString("FOTO_PERFIL_URI", uri.toString())
            .apply()
    }
}
package com.example.cuidapet.view

import com.example.cuidapet.model.Mascota
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MascotaAdapterTest {

    // Obtenemos una instancia del comparador que está dentro del Adapter
    private val diffCallback = MascotaAdapter.MascotaDiffCallback()

    @Test
    fun `areItemsTheSame con el mismo id debe devolver true`() {
        // Arrange
        val mascota1 = Mascota(id = 1, nombre = "Bobby", edad = "5", raza = "Golden", peso = "30", fotoUri = "", idUsuario = 1)
        val mascota2 = Mascota(id = 1, nombre = "Rex", edad = "2", raza = "Pastor", peso = "25", fotoUri = "", idUsuario = 1)

        // Act & Assert
        assertTrue(diffCallback.areItemsTheSame(mascota1, mascota2))
    }

    @Test
    fun `areItemsTheSame con diferente id debe devolver false`() {
        // Arrange
        val mascota1 = Mascota(id = 1, nombre = "Bobby", edad = "5", raza = "Golden", peso = "30", fotoUri = "", idUsuario = 1)
        val mascota2 = Mascota(id = 2, nombre = "Bobby", edad = "5", raza = "Golden", peso = "30", fotoUri = "", idUsuario = 1)

        // Act & Assert
        assertFalse(diffCallback.areItemsTheSame(mascota1, mascota2))
    }

    @Test
    fun `areContentsTheSame con el mismo contenido debe devolver true`() {
        // Arrange
        val mascota1 = Mascota(id = 1, nombre = "Bobby", edad = "5", raza = "Golden", peso = "30", fotoUri = "", idUsuario = 1)
        val mascota2 = Mascota(id = 1, nombre = "Bobby", edad = "5", raza = "Golden", peso = "30", fotoUri = "", idUsuario = 1)

        // Act & Assert
        assertTrue(diffCallback.areContentsTheSame(mascota1, mascota2))
    }

    @Test
    fun `areContentsTheSame con diferente contenido debe devolver false`() {
        // Arrange
        val mascota1 = Mascota(id = 1, nombre = "Bobby", edad = "5", raza = "Golden", peso = "30", fotoUri = "", idUsuario = 1)
        val mascota2 = Mascota(id = 1, nombre = "Bobby", edad = "6", raza = "Golden", peso = "32", fotoUri = "", idUsuario = 1)

        // Act & Assert
        assertFalse(diffCallback.areContentsTheSame(mascota1, mascota2))
    }
}

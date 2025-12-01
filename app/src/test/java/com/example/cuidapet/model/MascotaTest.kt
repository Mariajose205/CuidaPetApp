package com.example.cuidapet.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MascotaTest {

    @Test
    fun `dos mascotas con los mismos datos deben ser iguales`() {
        // Arrange
        val mascota1 = Mascota(1, "Bobby", "5", "Golden", "30", null, 1)
        val mascota2 = Mascota(1, "Bobby", "5", "Golden", "30", null, 1)

        // Assert
        assertEquals(mascota1, mascota2)
    }

    @Test
    fun `dos mascotas con diferentes datos no deben ser iguales`() {
        // Arrange
        val mascota1 = Mascota(1, "Bobby", "5", "Golden", "30", null, 1)
        val mascota2 = Mascota(2, "Rex", "2", "Pastor", "25", null, 1)

        // Assert
        assertNotEquals(mascota1, mascota2)
    }

    @Test
    fun `copy debe crear una copia con los datos modificados`() {
        // Arrange
        val mascotaOriginal = Mascota(1, "Bobby", "5", "Golden", "30", null, 1)

        // Act
        val mascotaCopiada = mascotaOriginal.copy(nombre = "Bobby Jr.")

        // Assert
        assertNotEquals(mascotaOriginal, mascotaCopiada)
        assertEquals("Bobby Jr.", mascotaCopiada.nombre)
        assertEquals(mascotaOriginal.id, mascotaCopiada.id)
    }
}

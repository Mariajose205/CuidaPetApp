package com.example.cuidapet.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UsuarioTest {

    @Test
    fun `dos usuarios con los mismos datos deben ser iguales`() {
        // Arrange
        val usuario1 = Usuario("Alejo", "01/01/1990", "alejo@test.com", "123456", null, 1)
        val usuario2 = Usuario("Alejo", "01/01/1990", "alejo@test.com", "123456", null, 1)

        // Assert
        assertEquals(usuario1, usuario2)
    }

    @Test
    fun `dos usuarios con diferentes datos no deben ser iguales`() {
        // Arrange
        val usuario1 = Usuario("Alejo", "01/01/1990", "alejo@test.com", "123456", null, 1)
        val usuario2 = Usuario("Maria", "02/02/1992", "maria@test.com", "abcdef", null, 2)

        // Assert
        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `copy debe crear una copia con los datos modificados`() {
        // Arrange
        val usuarioOriginal = Usuario("Alejo", "01/01/1990", "alejo@test.com", "123456", null, 1)

        // Act
        val usuarioCopiado = usuarioOriginal.copy(nombre = "Alejo Jr.")

        // Assert
        assertNotEquals(usuarioOriginal, usuarioCopiado)
        assertEquals("Alejo Jr.", usuarioCopiado.nombre)
        assertEquals(usuarioOriginal.id, usuarioCopiado.id)
        assertEquals(usuarioOriginal.correo, usuarioCopiado.correo)
    }
}

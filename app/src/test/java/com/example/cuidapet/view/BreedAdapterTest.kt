package com.example.cuidapet.view

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BreedAdapterTest {

    private val diffCallback = UsuarioAdapter.BreedDiffCallback()

    @Test
    fun `areItemsTheSame con el mismo string debe devolver true`() {
        // Arrange
        val breed1 = "poodle"
        val breed2 = "poodle"

        // Act & Assert
        assertTrue(diffCallback.areItemsTheSame(breed1, breed2))
    }

    @Test
    fun `areItemsTheSame con diferente string debe devolver false`() {
        // Arrange
        val breed1 = "poodle"
        val breed2 = "labrador"

        // Act & Assert
        assertFalse(diffCallback.areItemsTheSame(breed1, breed2))
    }

    @Test
    fun `areContentsTheSame con el mismo string debe devolver true`() {
        // Arrange
        val breed1 = "poodle"
        val breed2 = "poodle"

        // Act & Assert
        assertTrue(diffCallback.areContentsTheSame(breed1, breed2))
    }

    @Test
    fun `areContentsTheSame con diferente string debe devolver false`() {
        // Arrange
        val breed1 = "poodle"
        val breed2 = "labrador"

        // Act & Assert
        assertFalse(diffCallback.areContentsTheSame(breed1, breed2))
    }
}

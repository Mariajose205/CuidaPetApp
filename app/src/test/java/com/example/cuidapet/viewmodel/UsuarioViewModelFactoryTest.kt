package com.example.cuidapet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cuidapet.data.UsuarioDao
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class UsuarioViewModelFactoryTest {

    @Mock
    private lateinit var usuarioDao: UsuarioDao

    private lateinit var factory: UsuarioViewModelFactory

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        factory = UsuarioViewModelFactory(usuarioDao)
    }

    @Test
    fun `create con UsuarioViewModel class devuelve una instancia de UsuarioViewModel`() {
        val viewModel = factory.create(UsuarioViewModel::class.java)
        assertTrue(viewModel is UsuarioViewModel)
    }

    @Test
    fun `create con una clase desconocida lanza IllegalArgumentException`() {
        class OtherViewModel : ViewModel()
        assertThrows(IllegalArgumentException::class.java) {
            factory.create(OtherViewModel::class.java)
        }
    }
}

package com.example.cuidapet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cuidapet.data.MascotaDao
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class MascotaViewModelFactoryTest {

    @Mock
    private lateinit var mascotaDao: MascotaDao

    private lateinit var factory: MascotaViewModelFactory

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        factory = MascotaViewModelFactory(mascotaDao)
    }

    @Test
    fun `create con MascotaViewModel class devuelve una instancia de MascotaViewModel`() {
        val viewModel = factory.create(MascotaViewModel::class.java)
        assertTrue(viewModel is MascotaViewModel)
    }

    @Test
    fun `create con una clase desconocida lanza IllegalArgumentException`() {
        class OtherViewModel : ViewModel()
        assertThrows(IllegalArgumentException::class.java) {
            factory.create(OtherViewModel::class.java)
        }
    }
}

package com.example.cuidapet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cuidapet.network.DogApiService
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class DogViewModelFactoryTest {

    @Mock
    private lateinit var apiService: DogApiService

    private lateinit var factory: DogViewModelFactory

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        factory = DogViewModelFactory(apiService)
    }

    @Test
    fun `create con DogViewModel class devuelve una instancia de DogViewModel`() {
        val viewModel = factory.create(DogViewModel::class.java)
        assertTrue(viewModel is DogViewModel)
    }

    @Test
    fun `create con una clase desconocida lanza IllegalArgumentException`() {
        class OtherViewModel : ViewModel()
        assertThrows(IllegalArgumentException::class.java) {
            factory.create(OtherViewModel::class.java)
        }
    }
}

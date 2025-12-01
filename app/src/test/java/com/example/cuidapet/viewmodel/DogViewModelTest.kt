package com.example.cuidapet.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cuidapet.network.DogApiService
import com.example.cuidapet.network.DogBreedResponse
import com.example.cuidapet.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class DogViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var apiService: DogApiService

    private lateinit var viewModel: DogViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = DogViewModel(apiService)
    }

    @Test
    fun `fetchBreeds con éxito debe actualizar la lista de razas`() = runBlocking {
        val mockResponse = DogBreedResponse(
            message = mapOf("hound" to listOf("afghan", "basset"), "poodle" to emptyList()),
            status = "success"
        )
        `when`(apiService.getBreeds()).thenReturn(Response.success(mockResponse))

        viewModel.fetchBreeds()

        val breeds = viewModel.breeds.value
        assert(breeds != null)
        assert(breeds?.size == 3)
        assert(breeds?.contains("hound afghan") == true)
    }

    @Test
    fun `fetchBreeds con fallo debe actualizar el mensaje de error`() = runBlocking {
        val errorResponse = Response.error<DogBreedResponse>(404, okhttp3.ResponseBody.create(null, ""))
        `when`(apiService.getBreeds()).thenReturn(errorResponse)

        viewModel.fetchBreeds()

        assert(viewModel.error.value == "Error al obtener las razas: 404")
    }

    @Test
    fun `fetchBreeds con excepción debe actualizar el mensaje de error`() = runBlocking {
        val exception = RuntimeException("Error de red simulado")
        `when`(apiService.getBreeds()).thenThrow(exception)

        viewModel.fetchBreeds()

        assert(viewModel.error.value == "Excepción: ${exception.message}")
    }
}

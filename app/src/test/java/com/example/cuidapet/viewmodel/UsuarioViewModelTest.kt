package com.example.cuidapet.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cuidapet.data.UsuarioDao
import com.example.cuidapet.model.Usuario
import com.example.cuidapet.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UsuarioViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var usuarioDao: UsuarioDao

    @Mock
    private lateinit var uri: Uri

    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = UsuarioViewModel(usuarioDao)
    }

    @Test
    fun `registrarUsuario debe llamar al DAO con el usuario correcto`() = runBlocking {
        // Arrange
        val nombre = "Test User"
        val fecha = "01/01/2000"
        val correo = "test@test.com"
        val pass = "123456"
        val captor = argumentCaptor<Usuario>()

        // Act
        viewModel.registrarUsuario(nombre, fecha, correo, pass)

        // Assert
        verify(usuarioDao).registrar(captor.capture())
        val usuarioRegistrado = captor.firstValue
        assert(usuarioRegistrado.nombre == nombre)
        assert(usuarioRegistrado.correo == correo)
    }

    @Test
    fun `iniciarSesion con credenciales correctas debe devolver un usuario`() = runBlocking {
        // Arrange
        val correo = "test@test.com"
        val pass = "123456"
        val usuarioMock = Usuario("Test", "01/01/2000", correo, pass)
        `when`(usuarioDao.iniciarSesion(correo, pass)).thenReturn(usuarioMock)
        var resultado: Usuario? = null

        // Act
        viewModel.iniciarSesion(correo, pass) { resultado = it }

        // Assert
        assert(resultado != null)
        assert(resultado?.correo == correo)
    }

    @Test
    fun `iniciarSesion con credenciales incorrectas debe devolver null`() = runBlocking {
        // Arrange
        val correo = "wrong@test.com"
        val pass = "wrongpass"
        `when`(usuarioDao.iniciarSesion(correo, pass)).thenReturn(null)
        var resultado: Usuario? = Usuario("", "", "", "") // Valor inicial no nulo

        // Act
        viewModel.iniciarSesion(correo, pass) { resultado = it }

        // Assert
        assert(resultado == null)
    }

    @Test
    fun `actualizarFotoUsuario debe llamar al DAO con los datos correctos`() = runBlocking {
        // Arrange
        val userId = 1
        val fotoUriString = "content://path/to/image"
        `when`(uri.toString()).thenReturn(fotoUriString)

        // Act
        viewModel.actualizarFotoUsuario(userId, uri)

        // Assert
        verify(usuarioDao).actualizarFoto(userId, fotoUriString)
    }

    @Test
    fun `obtenerUsuarioPorId debe devolver el usuario correcto`() = runBlocking {
        // Arrange
        val userId = 1
        val usuarioMock = Usuario("Test", "01/01/2000", "test@test.com", "123456", id = userId)
        `when`(usuarioDao.obtenerUsuarioPorId(userId)).thenReturn(usuarioMock)
        var resultado: Usuario? = null

        // Act
        viewModel.obtenerUsuarioPorId(userId) { resultado = it }

        // Assert
        assert(resultado != null)
        assert(resultado?.id == userId)
    }
}

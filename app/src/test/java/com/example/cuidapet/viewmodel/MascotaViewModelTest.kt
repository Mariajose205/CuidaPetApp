package com.example.cuidapet.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.cuidapet.data.MascotaDao
import com.example.cuidapet.model.Mascota
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MascotaViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mascotaDao: MascotaDao

    private lateinit var viewModel: MascotaViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // Ahora podemos inyectar el DAO directamente
        viewModel = MascotaViewModel(mascotaDao)
    }

    @Test
    fun `obtenerMascotas debe llamar al DAO y devolver los datos`() {
        // 1. Preparación (Arrange)
        val usuarioId = 1
        val liveData = MutableLiveData<List<Mascota>>().apply { value = emptyList() }
        `when`(mascotaDao.obtenerMascotasPorUsuario(usuarioId)).thenReturn(liveData)

        // 2. Acción (Act)
        val resultado = viewModel.obtenerMascotas(usuarioId)

        // 3. Verificación (Assert)
        // Verificamos que se llamó al método del DAO
        verify(mascotaDao).obtenerMascotasPorUsuario(usuarioId)
        // Verificamos que el resultado es el mismo LiveData que devolvió el DAO
        assert(resultado == liveData)
    }
}

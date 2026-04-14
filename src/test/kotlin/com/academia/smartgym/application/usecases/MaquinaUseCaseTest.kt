package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Maquina
import com.academia.smartgym.domain.model.StatusMaquina
import com.academia.smartgym.domain.repository.MaquinaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class MaquinaUseCaseTest {

    @Mock
    private lateinit var maquinaRepository: MaquinaRepository

    private lateinit var maquinaUseCase: MaquinaUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        maquinaUseCase = MaquinaUseCase(maquinaRepository)
    }

    @Test
    fun `listarTodas deve retornar lista de maquinas`() {
        // Arrange
        val maquinas = listOf(
            Maquina(1, "Supino", "Sala 1", StatusMaquina.LIVRE),
            Maquina(2, "Leg Press", "Sala 2", StatusMaquina.OCUPADA)
        )
        whenever(maquinaRepository.findAll()).thenReturn(maquinas)

        // Act
        val resultado = maquinaUseCase.listarTodas()

        // Assert
        assertEquals(2, resultado.size)
        assertEquals("Supino", resultado[0].nome)
        assertEquals("Leg Press", resultado[1].nome)
        verify(maquinaRepository).findAll()
    }

    @Test
    fun `buscarPorId deve retornar maquina quando encontrada`() {
        // Arrange
        val maquina = Maquina(1, "Supino", "Sala 1", StatusMaquina.LIVRE)
        whenever(maquinaRepository.findById(1)).thenReturn(maquina)

        // Act
        val resultado = maquinaUseCase.buscarPorId(1)

        // Assert
        assertEquals("Supino", resultado.nome)
        assertEquals("Sala 1", resultado.localizacao)
        verify(maquinaRepository).findById(1)
    }

    @Test
    fun `buscarPorId deve lançar exception quando nao encontrada`() {
        // Arrange
        whenever(maquinaRepository.findById(999)).thenReturn(null)

        // Act & Assert
        assertThrows<Exception> {
            maquinaUseCase.buscarPorId(999)
        }
    }

    @Test
    fun `salvar deve chamar repository com maquina`() {
        // Arrange
        val maquina = Maquina(null, "Supino", "Sala 1", StatusMaquina.LIVRE)
        val maquinaSalva = maquina.copy(id = 1)
        whenever(maquinaRepository.save(any())).thenReturn(maquinaSalva)

        // Act
        val resultado = maquinaUseCase.salvar(maquina)

        // Assert
        assertEquals(1, resultado.id)
        assertEquals("Supino", resultado.nome)
        verify(maquinaRepository).save(maquina)
    }

    @Test
    fun `excluir deve chamar repository com id`() {
        // Act
        maquinaUseCase.excluir(1)

        // Assert
        verify(maquinaRepository).deleteById(1)
    }

    @Test
    fun `buscarPorNome deve retornar maquinas com nome similar`() {
        // Arrange
        val maquinas = listOf(
            Maquina(1, "Supino", "Sala 1", StatusMaquina.LIVRE),
            Maquina(3, "Supino Inclinado", "Sala 2", StatusMaquina.OCUPADA)
        )
        whenever(maquinaRepository.findByNomeContainingIgnoreCase("supi")).thenReturn(maquinas)

        // Act
        val resultado = maquinaUseCase.buscarPorNome("supi")

        // Assert
        assertEquals(2, resultado.size)
        assertEquals("Supino", resultado[0].nome)
        assertEquals("Supino Inclinado", resultado[1].nome)
        verify(maquinaRepository).findByNomeContainingIgnoreCase("supi")
    }

    @Test
    fun `buscarPorNome deve retornar lista vazia quando nenhuma maquina encontrada`() {
        // Arrange
        whenever(maquinaRepository.findByNomeContainingIgnoreCase("inexistente")).thenReturn(emptyList())

        // Act
        val resultado = maquinaUseCase.buscarPorNome("inexistente")

        // Assert
        assertEquals(0, resultado.size)
        verify(maquinaRepository).findByNomeContainingIgnoreCase("inexistente")
    }

    @Test
    fun `buscarPorNome deve ser case-insensitive`() {
        // Arrange
        val maquinas = listOf(
            Maquina(2, "Leg Press", "Sala 2", StatusMaquina.LIVRE)
        )
        whenever(maquinaRepository.findByNomeContainingIgnoreCase("LEG")).thenReturn(maquinas)

        // Act
        val resultado = maquinaUseCase.buscarPorNome("LEG")

        // Assert
        assertEquals(1, resultado.size)
        assertEquals("Leg Press", resultado[0].nome)
        verify(maquinaRepository).findByNomeContainingIgnoreCase("LEG")
    }
}


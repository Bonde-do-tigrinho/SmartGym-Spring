package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Exercicio
import com.academia.smartgym.domain.model.TipoExercicio
import com.academia.smartgym.domain.repository.ExercicioRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ExercicioUseCaseTest {

    @Mock
    private lateinit var exercicioRepository: ExercicioRepository

    private lateinit var exercicioUseCase: ExercicioUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        exercicioUseCase = ExercicioUseCase(exercicioRepository)
    }

    @Test
    fun `listarTodos deve retornar lista de exercicios`() {
        // Arrange
        val exercicios = listOf(
            Exercicio(1, "Supino", "Exercício de supino", TipoExercicio.MAQUINA, "todes"),
            Exercicio(2, "Flexão", "Flexão de braços", TipoExercicio.LIVRE, null)
        )
        whenever(exercicioRepository.findAll()).thenReturn(exercicios)

        // Act
        val resultado = exercicioUseCase.listarTodos()

        // Assert
        assertEquals(2, resultado.size)
        assertEquals("Supino", resultado[0].nome)
        assertEquals("Flexão", resultado[1].nome)
        verify(exercicioRepository).findAll()
    }

    @Test
    fun `buscarPorId deve retornar exercicio quando encontrado`() {
        // Arrange
        val exercicio = Exercicio(1, "Supino", "Exercício de supino", TipoExercicio.MAQUINA, "topdes")
        whenever(exercicioRepository.findById(1)).thenReturn(exercicio)

        // Act
        val resultado = exercicioUseCase.buscarPorId(1)

        // Assert
        assertEquals("Supino", resultado.nome)
        verify(exercicioRepository).findById(1)
    }

    @Test
    fun `buscarPorId deve lançar exception quando nao encontrado`() {
        // Arrange
        whenever(exercicioRepository.findById(999)).thenReturn(null)

        // Act & Assert
        assertThrows<Exception> {
            exercicioUseCase.buscarPorId(999)
        }
    }

    @Test
    fun `salvar deve chamar repository com exercicio`() {
        // Arrange
        val exercicio = Exercicio(null, "Supino", "Exercício de supino", TipoExercicio.MAQUINA, "todes")
        val exercicioSalvo = exercicio.copy(id = 1)
        whenever(exercicioRepository.save(any())).thenReturn(exercicioSalvo)

        // Act
        val resultado = exercicioUseCase.salvar(exercicio)

        // Assert
        assertEquals(1, resultado.id)
        verify(exercicioRepository).save(exercicio)
    }

    @Test
    fun `excluir deve chamar repository com id`() {
        // Act
        exercicioUseCase.excluir(1)

        // Assert
        verify(exercicioRepository).deleteById(1)
    }

    @Test
    fun `listarPorMaquina deve retornar exercicios da maquina`() {
        // Arrange
        val exercicios = listOf(
            Exercicio(1, "Supino", "Exercício de supino", TipoExercicio.MAQUINA, "todes"),
            Exercicio(3, "Supino Inclinado", "Supino inclinado", TipoExercicio.MAQUINA, "todes")
        )
        whenever(exercicioRepository.findByMaquinaId(1)).thenReturn(exercicios)

        // Act
        val resultado = exercicioUseCase.listarPorMaquina(1)

        // Assert
        assertEquals(2, resultado.size)
        assertEquals(1L, resultado[0].maquinaId)
        assertEquals(1L, resultado[1].maquinaId)
        verify(exercicioRepository).findByMaquinaId(1)
    }
}


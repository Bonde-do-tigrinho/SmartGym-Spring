package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.*
import com.academia.smartgym.domain.repository.ExercicioRepository
import com.academia.smartgym.domain.repository.FichaTreinoRepository
import com.academia.smartgym.domain.repository.UsuarioRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class FichaTreinoUseCaseTest {

    // Mock do repositório principal de fichas de treino
    @Mock
    private lateinit var fichaTreinoRepository: FichaTreinoRepository

    // Mock do repositório de usuários (usado para validar se o aluno existe)
    @Mock
    private lateinit var usuarioRepository: UsuarioRepository

    // Mock do repositório de exercícios (usado para validar referências dos exercícios)
    @Mock
    private lateinit var exercicioRepository: ExercicioRepository

    private lateinit var fichaTreinoUseCase: FichaTreinoUseCase

    // ─── Objetos auxiliares reutilizados nos testes ───────────────────────────

    private val alunoValido = Usuario(
        id = 1,
        nome = "João Silva",
        email = "joao@email.com",
        role = UserRole.ALUNO,
        cpf = "12345678901",
        telefone = "11999999999"
    )

    private val exercicioValido = Exercicio(
        id = 1L,
        nome = "Supino",
        descricao = "Exercício de peito",
        tipo = TipoExercicio.MAQUINA,
        grupoMuscular = "Peito"
    )

    private val exercicioItemValido = ExercicioFichaTreino(
        exercicioId = 1L,
        series = 3,
        repeticoes = 12,
        descansoSegundos = 60
    )

    private val fichaValida = FichaTreino(
        id = null,
        alunoId = 1,
        exercicios = listOf(exercicioItemValido),
        vigencia = Date(),
        focoTreino = "Peito"
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fichaTreinoUseCase = FichaTreinoUseCase(
            fichaTreinoRepository,
            usuarioRepository,
            exercicioRepository
        )
    }

    // ─── listarTodas ──────────────────────────────────────────────────────────

    @Test
    fun `listarTodas deve retornar lista de fichas de treino`() {
        // Arrange
        val fichas = listOf(
            fichaValida.copy(id = 1L, focoTreino = "Peito"),
            fichaValida.copy(id = 2L, focoTreino = "Costas")
        )
        // Mock: fichaTreinoRepository.findAll() retorna lista com 2 fichas
        whenever(fichaTreinoRepository.findAll()).thenReturn(fichas)

        // Act
        val resultado = fichaTreinoUseCase.listarTodas()

        // Assert
        assertEquals(2, resultado.size)
        assertEquals("Peito", resultado[0].focoTreino)
        assertEquals("Costas", resultado[1].focoTreino)
        verify(fichaTreinoRepository).findAll()
    }

    @Test
    fun `listarTodas deve retornar lista vazia quando nao houver fichas`() {
        // Mock: fichaTreinoRepository.findAll() retorna lista vazia
        whenever(fichaTreinoRepository.findAll()).thenReturn(emptyList())

        // Act
        val resultado = fichaTreinoUseCase.listarTodas()

        // Assert
        assertEquals(0, resultado.size)
        verify(fichaTreinoRepository).findAll()
    }

    // ─── buscarPorId ──────────────────────────────────────────────────────────

    @Test
    fun `buscarPorId deve retornar ficha quando encontrada`() {
        // Arrange
        val ficha = fichaValida.copy(id = 1L)
        // Mock: fichaTreinoRepository.findById(1) retorna a ficha
        whenever(fichaTreinoRepository.findById(1L)).thenReturn(ficha)

        // Act
        val resultado = fichaTreinoUseCase.buscarPorId(1L)

        // Assert
        assertEquals(1L, resultado.id)
        assertEquals("Peito", resultado.focoTreino)
        verify(fichaTreinoRepository).findById(1L)
    }

    @Test
    fun `buscarPorId deve lancar RuntimeException quando ficha nao for encontrada`() {
        // Mock: fichaTreinoRepository.findById(999) retorna null (não encontrado)
        whenever(fichaTreinoRepository.findById(999L)).thenReturn(null)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.buscarPorId(999L)
        }
        assertEquals("Ficha de treino não encontrada", excecao.message)
        verify(fichaTreinoRepository).findById(999L)
    }

    // ─── listarPorAluno ───────────────────────────────────────────────────────

    @Test
    fun `listarPorAluno deve retornar fichas do aluno informado`() {
        // Arrange
        val fichas = listOf(
            fichaValida.copy(id = 1L, focoTreino = "Peito"),
            fichaValida.copy(id = 2L, focoTreino = "Ombro")
        )
        // Mock: fichaTreinoRepository.findByAlunoId(1) retorna fichas do aluno
        whenever(fichaTreinoRepository.findByAlunoId(1)).thenReturn(fichas)

        // Act
        val resultado = fichaTreinoUseCase.listarPorAluno(1)

        // Assert
        assertEquals(2, resultado.size)
        assertEquals(1, resultado[0].alunoId)
        verify(fichaTreinoRepository).findByAlunoId(1)
    }

    @Test
    fun `listarPorAluno deve retornar lista vazia quando aluno nao tiver fichas`() {
        // Mock: fichaTreinoRepository.findByAlunoId(99) retorna lista vazia
        whenever(fichaTreinoRepository.findByAlunoId(99)).thenReturn(emptyList())

        // Act
        val resultado = fichaTreinoUseCase.listarPorAluno(99)

        // Assert
        assertEquals(0, resultado.size)
        verify(fichaTreinoRepository).findByAlunoId(99)
    }

    // ─── salvar (cenários de sucesso) ─────────────────────────────────────────

    @Test
    fun `salvar deve persistir ficha valida com sucesso`() {
        // Arrange
        val fichaSalva = fichaValida.copy(id = 1L)
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)
        // Mock: exercicioRepository.findById(1) retorna exercício válido
        whenever(exercicioRepository.findById(1L)).thenReturn(exercicioValido)
        // Mock: fichaTreinoRepository.save(...) retorna ficha com id gerado
        whenever(fichaTreinoRepository.save(any())).thenReturn(fichaSalva)

        // Act
        val resultado = fichaTreinoUseCase.salvar(fichaValida)

        // Assert
        assertEquals(1L, resultado.id)
        assertEquals("Peito", resultado.focoTreino)
        verify(fichaTreinoRepository).save(any())
    }

    @Test
    fun `salvar deve remover exercicios duplicados pelo exercicioId antes de persistir`() {
        // Arrange — ficha com exercício duplicado (mesmo exercicioId)
        val exercicioDuplicado = exercicioItemValido.copy()   // exercicioId = 1L (repetido)
        val fichaComDuplicados = fichaValida.copy(
            exercicios = listOf(exercicioItemValido, exercicioDuplicado)
        )
        val fichaSalva = fichaValida.copy(id = 1L, exercicios = listOf(exercicioItemValido))
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)
        // Mock: exercicioRepository.findById(1) retorna exercício válido
        whenever(exercicioRepository.findById(1L)).thenReturn(exercicioValido)
        // Mock: fichaTreinoRepository.save(...) retorna ficha normalizada
        whenever(fichaTreinoRepository.save(any())).thenReturn(fichaSalva)

        // Act
        val resultado = fichaTreinoUseCase.salvar(fichaComDuplicados)

        // Assert — deve salvar apenas 1 exercício (deduplicado)
        assertEquals(1, resultado.exercicios.size)
        verify(fichaTreinoRepository).save(any())
    }

    // ─── salvar (cenários de validação — aluno) ───────────────────────────────

    @Test
    fun `salvar deve lancar RuntimeException quando aluno nao for encontrado`() {
        // Mock: usuarioRepository.findById(1) retorna null (aluno inexistente)
        whenever(usuarioRepository.findById(1)).thenReturn(null)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.salvar(fichaValida)
        }
        assertEquals("Aluno com id 1 não encontrado", excecao.message)
        // Garante que o repositório de fichas nunca foi chamado
        verify(fichaTreinoRepository, never()).save(any())
    }

    // ─── salvar (cenários de validação — exercícios) ──────────────────────────

    @Test
    fun `salvar deve lancar RuntimeException quando lista de exercicios estiver vazia`() {
        // Arrange
        val fichaSemExercicios = fichaValida.copy(exercicios = emptyList())
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.salvar(fichaSemExercicios)
        }
        assertEquals("A ficha de treino deve ter ao menos 1 exercício", excecao.message)
        verify(fichaTreinoRepository, never()).save(any())
    }

    @Test
    fun `salvar deve lancar RuntimeException quando series for zero ou negativo`() {
        // Arrange
        val exercicioSeriesInvalidas = exercicioItemValido.copy(series = 0)
        val fichaInvalida = fichaValida.copy(exercicios = listOf(exercicioSeriesInvalidas))
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.salvar(fichaInvalida)
        }
        assertEquals("Séries deve ser maior que 0", excecao.message)
        verify(fichaTreinoRepository, never()).save(any())
    }

    @Test
    fun `salvar deve lancar RuntimeException quando repeticoes for zero ou negativo`() {
        // Arrange
        val exercicioRepeticoesInvalidas = exercicioItemValido.copy(repeticoes = 0)
        val fichaInvalida = fichaValida.copy(exercicios = listOf(exercicioRepeticoesInvalidas))
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.salvar(fichaInvalida)
        }
        assertEquals("Repetições deve ser maior que 0", excecao.message)
        verify(fichaTreinoRepository, never()).save(any())
    }

    @Test
    fun `salvar deve lancar RuntimeException quando descanso for negativo`() {
        // Arrange
        val exercicioDescansoInvalido = exercicioItemValido.copy(descansoSegundos = -1)
        val fichaInvalida = fichaValida.copy(exercicios = listOf(exercicioDescansoInvalido))
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.salvar(fichaInvalida)
        }
        assertEquals("Descanso não pode ser negativo", excecao.message)
        verify(fichaTreinoRepository, never()).save(any())
    }

    @Test
    fun `salvar deve lancar RuntimeException quando exercicio referenciado nao existir`() {
        // Arrange
        val exercicioInexistente = exercicioItemValido.copy(exercicioId = 99L)
        val fichaComExercicioInvalid = fichaValida.copy(exercicios = listOf(exercicioInexistente))
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)
        // Mock: exercicioRepository.findById(99) retorna null (exercício inexistente)
        whenever(exercicioRepository.findById(99L)).thenReturn(null)

        // Act & Assert
        val excecao = assertThrows<RuntimeException> {
            fichaTreinoUseCase.salvar(fichaComExercicioInvalid)
        }
        assertEquals("Exercícios não encontrados: [99]", excecao.message)
        verify(fichaTreinoRepository, never()).save(any())
    }

    @Test
    fun `salvar deve aceitar descanso igual a zero`() {
        // Arrange — descansoSegundos = 0 é válido (>= 0)
        val exercicioSemDescanso = exercicioItemValido.copy(descansoSegundos = 0)
        val fichaValida2 = fichaValida.copy(exercicios = listOf(exercicioSemDescanso))
        val fichaSalva = fichaValida2.copy(id = 1L)
        // Mock: usuarioRepository.findById(1) retorna aluno válido
        whenever(usuarioRepository.findById(1)).thenReturn(alunoValido)
        // Mock: exercicioRepository.findById(1) retorna exercício válido
        whenever(exercicioRepository.findById(1L)).thenReturn(exercicioValido)
        // Mock: fichaTreinoRepository.save(...) retorna ficha salva
        whenever(fichaTreinoRepository.save(any())).thenReturn(fichaSalva)

        // Act & Assert — não deve lançar exceção
        val resultado = fichaTreinoUseCase.salvar(fichaValida2)
        assertEquals(1L, resultado.id)
        verify(fichaTreinoRepository).save(any())
    }

    // ─── excluir ──────────────────────────────────────────────────────────────

    @Test
    fun `excluir deve chamar repository com o id informado`() {
        // Act
        fichaTreinoUseCase.excluir(1L)

        // Assert
        // Mock: verifica que fichaTreinoRepository.deleteById(1) foi chamado
        verify(fichaTreinoRepository).deleteById(1L)
    }
}


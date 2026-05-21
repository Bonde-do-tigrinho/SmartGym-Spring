package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.domain.repository.ExercicioRepository
import com.academia.smartgym.domain.repository.FichaTreinoRepository
import org.springframework.stereotype.Service
import kotlin.compareTo

@Service
class FichaTreinoUseCase(
    private val repository: FichaTreinoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val exercicioRepository: ExercicioRepository
) {
    fun listarTodas() = repository.findAll()

    fun buscarPorId(id: Long) =
        repository.findById(id) ?: throw RuntimeException("Ficha de treino não encontrada")

    fun listarPorAluno(alunoId: Int) = repository.findByAlunoId(alunoId)

    fun salvar(ficha: FichaTreino): FichaTreino {
        val normalizada = ficha.copy(
            exercicios = ficha.exercicios.distinctBy { it.exercicioId }
        )
        validarReferencias(normalizada)
        return repository.save(normalizada)
    }

    fun excluir(id: Long) = repository.deleteById(id)

    private fun validarReferencias(ficha: FichaTreino) {
        if (usuarioRepository.findById(ficha.alunoId) == null) {
            throw RuntimeException("Aluno com id ${ficha.alunoId} não encontrado")
        }

        if (ficha.exercicios.isEmpty()) {
            throw RuntimeException("A ficha de treino deve ter ao menos 1 exercício")
        }

        ficha.exercicios.forEach {
            if (it.series <= 0) throw RuntimeException("Séries deve ser maior que 0")
            if (it.repeticoes <= 0) throw RuntimeException("Repetições deve ser maior que 0")
            if (it.descansoSegundos < 0) throw RuntimeException("Descanso não pode ser negativo")
        }

        val exerciciosInexistentes = ficha.exercicios
            .map { it.exercicioId }
            .filter { exercicioRepository.findById(it) == null }

        if (exerciciosInexistentes.isNotEmpty()) {
            throw RuntimeException("Exercícios não encontrados: $exerciciosInexistentes")
        }
    }
}
package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.domain.repository.ExercicioRepository
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.domain.repository.FichaTreinoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FichaTreinoUseCase(
    private val repository: FichaTreinoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val exercicioRepository: ExercicioRepository
) {
    fun listarTodas() = repository.findAll()
    fun buscarPorId(id: Int) = repository.findById(id) ?: throw RuntimeException("Ficha de treino não encontrada")
    fun listarPorAluno(alunoId: Int?) = repository.findByAlunoId(alunoId)
    fun excluir(id: Int) = repository.deleteById(id)

    @Transactional
    fun salvar(ficha: FichaTreino): FichaTreino {
        // Remove exercícios duplicados de dentro de cada dia da rotina de forma limpa
        val normalizada = ficha.copy(
            rotinaDias = ficha.rotinaDias.map { dia ->
                dia.copy(exercicios = dia.exercicios.distinctBy { it.exercicioId })
            }
        )

        validarReferencias(normalizada)
        return repository.save(normalizada)
    }

    fun listarPorProfessor(professorId: Int?): List<FichaTreino> {
        if (professorId != null) {
            if (professorId <= 0) {
                return emptyList()
            }
        }

        return repository.findByProfessorId(professorId)
    }

    private fun validarReferencias(ficha: FichaTreino) {
        if (usuarioRepository.findById(ficha.alunoId) == null) {
            throw RuntimeException("Aluno com id ${ficha.alunoId} não encontrado")
        }

        if (ficha.professorId != null && usuarioRepository.findById(ficha.professorId) == null) {
            throw RuntimeException("Professor com id ${ficha.professorId} não encontrado")
        }

        if (ficha.rotinaDias.isEmpty()) {
            throw RuntimeException("A ficha de treino deve ter ao menos 1 dia de treino (Ex: Treino A)")
        }

        val todosExerciciosDaFicha = ficha.rotinaDias.flatMap { it.exercicios }

        if (todosExerciciosDaFicha.isEmpty()) {
            throw RuntimeException("A ficha deve conter ao menos 1 exercício distribuído em seus dias")
        }

        todosExerciciosDaFicha.forEach {
            if (it.series <= 0) throw RuntimeException("Séries deve ser maior que 0")
            if (it.repeticoes <= 0) throw RuntimeException("Repetições deve ser maior que 0")
            if (it.descansoSegundos < 0) throw RuntimeException("Descanso não pode ser negativo")
        }

        val exerciciosInexistentes = todosExerciciosDaFicha
            .map { it.exercicioId }
            .distinct()
            .filter { exercicioRepository.findById(it) == null }

        if (exerciciosInexistentes.isNotEmpty()) {
            throw RuntimeException("Exercícios não encontrados no sistema: $exerciciosInexistentes")
        }
    }
}
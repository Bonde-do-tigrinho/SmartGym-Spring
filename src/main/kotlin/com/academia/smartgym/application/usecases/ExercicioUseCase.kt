package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Exercicio
import com.academia.smartgym.domain.repository.ExercicioRepository
import org.springframework.stereotype.Service

@Service
class ExercicioUseCase(private val repository: ExercicioRepository) {
    fun listarTodos() = repository.findAll()

    fun buscarPorId(id: Long) =
        repository.findById(id) ?: throw Exception("Exercício não encontrado")

    fun salvar(exercicio: Exercicio) = repository.save(exercicio)

    fun excluir(id: Long) = repository.deleteById(id)

    fun listarPorMaquina(maquinaId: Long) = repository.findByMaquinaId(maquinaId)

    fun buscarPorNome(nome: String) =
        repository.findByNomeContainingIgnoreCase(nome)

}


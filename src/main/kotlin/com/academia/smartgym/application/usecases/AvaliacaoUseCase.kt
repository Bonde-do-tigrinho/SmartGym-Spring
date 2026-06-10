package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Avaliacao
import com.academia.smartgym.domain.repository.AvaliacaoRepository
import org.springframework.stereotype.Service

@Service
class AvaliacaoUseCase(private val repository: AvaliacaoRepository) {

    fun listarTodas() = repository.findAll()

    fun buscarPorId(id: Long) =
        repository.findById(id) ?: throw Exception("Avaliação não encontrada")

    fun salvar(avaliacao: Avaliacao) = repository.save(avaliacao)

    fun excluir(id: Long) = repository.deleteById(id)

    fun listarPorAluno(alunoId: Int) = repository.findByAlunoId(alunoId)

    fun buscarPorNomeAluno(nome: String) =
        repository.findByNomeAlunoContainingIgnoreCase(nome)

    fun buscarPorProfessor(id: Int?) = repository.findByProfessorId(id)
}


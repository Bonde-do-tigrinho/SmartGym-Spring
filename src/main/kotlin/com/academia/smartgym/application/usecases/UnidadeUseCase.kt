package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Unidade
import com.academia.smartgym.domain.repository.UnidadeRepository
import org.springframework.stereotype.Service

@Service
class UnidadeUseCase(private val repository: UnidadeRepository) {

    fun listarTodas(): List<Unidade> = repository.findAll()

    fun buscarPorId(id: Long): Unidade {
        return repository.findById(id) ?: throw RuntimeException("Unidade não encontrada")
    }

    fun salvar(unidade: Unidade): Unidade = repository.save(unidade)

    fun atualizar(id: Long, unidade: Unidade): Unidade {
        val existente = buscarPorId(id)
        val unidadeAtualizada = existente.copy(
            nome = unidade.nome,
            endereco = unidade.endereco,
            cidade = unidade.cidade
        )
        return repository.save(unidadeAtualizada)
    }

    fun deletar(id: Long) {
        buscarPorId(id) // Verifica se existe antes de deletar
        repository.deleteById(id)
    }
}
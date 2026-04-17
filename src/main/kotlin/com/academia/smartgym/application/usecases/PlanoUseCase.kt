package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Plano
import com.academia.smartgym.domain.repository.PlanoRepository
import org.springframework.stereotype.Service

@Service
class PlanoUseCase(private val repository: PlanoRepository) {

    fun salvar(plano: Plano): Plano = repository.save(plano)

    fun listarTodos(): List<Plano> = repository.findAll()

    fun buscarPorId(id: Long): Plano? = repository.findById(id).orElse(null)

    fun atualizar(id: Long, planoAtualizado: Plano): Plano {
        val planoExistente = repository.findById(id).orElseThrow { RuntimeException("Plano não encontrado") }

        planoExistente.nome = planoAtualizado.nome
        planoExistente.ativo = planoAtualizado.ativo
        planoExistente.dataFimPromocao = planoAtualizado.dataFimPromocao
        planoExistente.horarioLimiteAcesso = planoAtualizado.horarioLimiteAcesso

        return repository.save(planoExistente)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}
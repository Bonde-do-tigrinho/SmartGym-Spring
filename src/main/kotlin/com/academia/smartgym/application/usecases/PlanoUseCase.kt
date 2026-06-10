package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Plano
import com.academia.smartgym.domain.repository.PlanoRepository
import org.springframework.stereotype.Service

@Service
class PlanoUseCase(private val planoRepository: PlanoRepository) {

    fun findAll(): List<Plano> = planoRepository.findAll()

    fun findById(id: Int): Plano? = planoRepository.findById(id)

    fun create(plano: Plano): Plano = planoRepository.save(plano)

    fun update(id: Int, planoAtualizado: Plano): Plano {
        val planoExistente = planoRepository.findById(id) ?: throw RuntimeException("Plano não encontrado")

        val updatedPlano = planoExistente.copy(
            nome = planoAtualizado.nome,
            descricao = planoAtualizado.descricao,
            valor = planoAtualizado.valor,
            duracaoMeses = planoAtualizado.duracaoMeses,
            ativo = planoAtualizado.ativo
        )

        return planoRepository.save(updatedPlano)
    }

    fun delete(id: Int) {
        planoRepository.findById(id) ?: throw RuntimeException("Plano não encontrado")
        planoRepository.deleteById(id)
    }
}
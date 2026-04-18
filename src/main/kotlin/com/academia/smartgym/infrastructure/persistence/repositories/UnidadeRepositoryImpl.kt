package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Unidade
import com.academia.smartgym.domain.repository.UnidadeRepository
import com.academia.smartgym.infrastructure.persistence.entities.UnidadeEntity
import org.springframework.stereotype.Component

@Component
class UnidadeRepositoryImpl(
    private val springRepository: SpringUnidadeRepository
) : UnidadeRepository {

    override fun findAll(): List<Unidade> {
        return springRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: Long): Unidade? {
        return springRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun save(unidade: Unidade): Unidade {
        val entity = UnidadeEntity(
            id = unidade.id,
            nome = unidade.nome,
            endereco = unidade.endereco,
            cidade = unidade.cidade
        )
        return springRepository.save(entity).toDomain()
    }

    override fun deleteById(id: Long) {
        springRepository.deleteById(id)
    }

    // Funções de conversão
    private fun UnidadeEntity.toDomain() = Unidade(id, nome, endereco, cidade)
}
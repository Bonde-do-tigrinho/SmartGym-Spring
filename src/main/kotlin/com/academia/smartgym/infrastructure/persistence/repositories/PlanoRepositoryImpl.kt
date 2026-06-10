package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Plano
import com.academia.smartgym.domain.repository.PlanoRepository
import com.academia.smartgym.infrastructure.persistence.mappers.toDomain
import com.academia.smartgym.infrastructure.persistence.mappers.toEntity
import org.springframework.stereotype.Component

@Component
class PlanoRepositoryImpl(
    private val springPlanoRepository: SpringPlanoRepository
) : PlanoRepository {
    override fun findAll(): List<Plano> = springPlanoRepository.findAll().map { it.toDomain() }

    override fun findById(id: Int): Plano? = springPlanoRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(plano: Plano): Plano = springPlanoRepository.save(plano.toEntity()).toDomain()

    override fun deleteById(id: Int) = springPlanoRepository.deleteById(id)
}
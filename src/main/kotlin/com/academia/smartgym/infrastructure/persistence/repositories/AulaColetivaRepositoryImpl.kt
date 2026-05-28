package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.AulaColetiva
import com.academia.smartgym.domain.repository.AulaColetivaRepository
import com.academia.smartgym.infrastructure.persistence.entities.AulaColetivaEntity
import com.academia.smartgym.infrastructure.persistence.mappers.AulaColetivaMapper
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AulaColetivaRepositoryImpl(
    private val springRepository: SpringAulaColetivaRepository,
    private val mapper: AulaColetivaMapper
) : AulaColetivaRepository {

    override fun save(aula: AulaColetiva): AulaColetiva {
        val entity = aula.toEntity()
        return springRepository.save(entity).toDomain()
    }

    override fun findById(id: Long): AulaColetiva? {
        return springRepository.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun buscarPorPeriodo(inicio: LocalDateTime, fim: LocalDateTime): List<AulaColetiva> {
        return springRepository.findByDataHoraInicioBetween(inicio, fim).map { it.toDomain() }
    }

    override fun deleteById(id: Long) = springRepository.deleteById(id)

    private fun AulaColetivaEntity.toDomain() = mapper.run { this@toDomain.toDomain() }
    private fun AulaColetiva.toEntity() = mapper.run { this@toEntity.toEntity() }

}
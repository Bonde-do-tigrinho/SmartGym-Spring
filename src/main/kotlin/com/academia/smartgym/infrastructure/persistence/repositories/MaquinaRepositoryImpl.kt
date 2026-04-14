package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Maquina
import com.academia.smartgym.domain.repository.MaquinaRepository
import com.academia.smartgym.infrastructure.persistence.entities.MaquinaEntity
import com.academia.smartgym.infrastructure.persistence.mappers.MaquinaMapper
import org.springframework.stereotype.Repository

@Repository
class MaquinaRepositoryImpl(
    private val springMaquinaRepository: SpringMaquinaRepository,
    private val mapper: MaquinaMapper
) : MaquinaRepository {

    override fun findAll(): List<Maquina> =
        springMaquinaRepository.findAll().map { it.toDomain() }

    override fun findById(id: Long): Maquina? =
        springMaquinaRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(maquina: Maquina): Maquina {
        val entity = maquina.toEntity()
        val saved = springMaquinaRepository.save(entity)
        return saved.toDomain()
    }

    override fun deleteById(id: Long) =
        springMaquinaRepository.deleteById(id)

    override fun findByNomeContainingIgnoreCase(nome: String): List<Maquina> =
        springMaquinaRepository.findByNomeContainingIgnoreCase(nome).map { it.toDomain() }

    // Extension functions to use mapper
    private fun MaquinaEntity.toDomain() = mapper.run { this@toDomain.toDomain() }
    private fun Maquina.toEntity() = mapper.run { this@toEntity.toEntity() }
}

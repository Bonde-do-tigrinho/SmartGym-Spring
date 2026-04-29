package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.domain.repository.FichaTreinoRepository
import com.academia.smartgym.infrastructure.persistence.entities.FichaTreinoEntity
import com.academia.smartgym.infrastructure.persistence.mappers.FichaTreinoMapper
import org.springframework.stereotype.Repository

@Repository
class FichaTreinoRepositoryImpl(
    private val springRepository: SpringFichaTreinoRepository,
    private val mapper: FichaTreinoMapper
) : FichaTreinoRepository {

    override fun findAll(): List<FichaTreino> =
        springRepository.findAll().map { it.toDomain() }

    override fun findById(id: Long): FichaTreino? =
        springRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(fichaTreino: FichaTreino): FichaTreino {
        val saved = springRepository.save(fichaTreino.toEntity())
        return saved.toDomain()
    }

    override fun deleteById(id: Long) =
        springRepository.deleteById(id)

    override fun findByAlunoId(alunoId: Int): List<FichaTreino> =
        springRepository.findByAlunoId(alunoId).map { it.toDomain() }

    private fun FichaTreinoEntity.toDomain() = mapper.run { this@toDomain.toDomain() }
    private fun FichaTreino.toEntity() = mapper.run { this@toEntity.toEntity() }
}

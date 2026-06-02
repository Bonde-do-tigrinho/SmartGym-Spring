package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Exercicio
import com.academia.smartgym.domain.repository.ExercicioRepository
import com.academia.smartgym.infrastructure.persistence.entities.ExercicioEntity
import com.academia.smartgym.infrastructure.persistence.mappers.ExercicioMapper
import org.springframework.stereotype.Repository

@Repository
class ExercicioRepositoryImpl(
    private val springExercicioRepository: SpringExercicioRepository,
    private val mapper: ExercicioMapper
) : ExercicioRepository {

    override fun findAll(): List<Exercicio> =
        springExercicioRepository.findAll().map { it.toDomain() }

    override fun findById(id: Int): Exercicio? =
        springExercicioRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(exercicio: Exercicio): Exercicio {
        val entity = exercicio.toEntity()
        val saved = springExercicioRepository.save(entity)
        return saved.toDomain()
    }

    override fun deleteById(id: Int) =
        springExercicioRepository.deleteById(id)

    override fun findByMaquinaId(maquinaId: Int): List<Exercicio> =
        springExercicioRepository.findByMaquinaId(maquinaId).map { it.toDomain() }

    override fun findByNomeContainingIgnoreCase(nome: String): List<Exercicio> =
        springExercicioRepository.findByNomeContainingIgnoreCase(nome).map { it.toDomain() }

    // Extension functions to use mapper
    private fun ExercicioEntity.toDomain() = mapper.run { this@toDomain.toDomain() }
    private fun Exercicio.toEntity() = mapper.run { this@toEntity.toEntity() }
}

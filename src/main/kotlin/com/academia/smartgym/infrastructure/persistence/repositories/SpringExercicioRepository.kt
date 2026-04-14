package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.ExercicioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringExercicioRepository : JpaRepository<ExercicioEntity, Long> {
    fun findByMaquinaId(maquinaId: Long): List<ExercicioEntity>

    fun findByNomeContainingIgnoreCase(nome: String): List<ExercicioEntity>

}


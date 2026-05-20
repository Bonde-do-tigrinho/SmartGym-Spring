package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.MaquinaIotEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringMaquinaIotRepository : JpaRepository<MaquinaIotEntity, String> {
    fun findByNomeContainingIgnoreCase(nome: String): List<MaquinaIotEntity>
}
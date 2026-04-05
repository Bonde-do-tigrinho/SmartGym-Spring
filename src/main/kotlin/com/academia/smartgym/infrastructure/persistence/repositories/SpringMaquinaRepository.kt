package com.academia.smartgym.infrastructure.persistence.repositories

import com.example.smartgym.infrastructure.persistence.entities.MaquinaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringMaquinaRepository : JpaRepository<MaquinaEntity, Long> {
}
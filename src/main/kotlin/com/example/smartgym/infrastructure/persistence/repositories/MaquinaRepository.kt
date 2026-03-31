package com.example.smartgym.infrastructure.persistence.repositories

import com.example.smartgym.infrastructure.persistence.entities.MaquinaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MaquinaRepository : JpaRepository<MaquinaEntity, Long> {
}
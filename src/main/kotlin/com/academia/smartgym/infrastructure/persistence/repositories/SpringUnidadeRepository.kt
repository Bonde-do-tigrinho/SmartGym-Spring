package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.UnidadeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringUnidadeRepository : JpaRepository<UnidadeEntity, Long>
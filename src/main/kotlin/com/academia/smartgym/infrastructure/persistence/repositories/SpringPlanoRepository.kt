package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.PlanoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringPlanoRepository : JpaRepository<PlanoEntity, Long>
package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.VerificationTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpringVerificationTokenRepository : JpaRepository<VerificationTokenEntity, Int> {
    fun findByToken(token: String): VerificationTokenEntity?
    fun deleteByUsuarioId(usuarioId: Int)
}
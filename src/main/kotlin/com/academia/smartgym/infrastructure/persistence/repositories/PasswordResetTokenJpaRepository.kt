package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.PasswordResetTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordResetTokenJpaRepository : JpaRepository<PasswordResetTokenEntity, Int> {
    fun findByToken(token: String): PasswordResetTokenEntity?
    fun deleteByUsuarioId(usuarioId: Int)
}
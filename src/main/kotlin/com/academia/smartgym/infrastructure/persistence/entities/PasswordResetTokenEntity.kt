package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "password_reset_tokens")
data class PasswordResetTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(name = "usuario_id", nullable = false)
    val usuarioId: Int,

    @Column(nullable = false)
    val expiracao: LocalDateTime
)
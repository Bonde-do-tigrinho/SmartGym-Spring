package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "verification_tokens")
data class VerificationTokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(nullable = false)
    val usuarioId: Int,

    @Column(nullable = false)
    val expiracao: LocalDateTime
)
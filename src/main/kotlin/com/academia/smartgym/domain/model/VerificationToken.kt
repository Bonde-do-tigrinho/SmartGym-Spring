package com.academia.smartgym.domain.model

import java.time.LocalDateTime

data class VerificationToken(
    val id: Int? = null,
    val token: String,
    val usuarioId: Int,
    val expiracao: LocalDateTime
)
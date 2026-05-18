package com.academia.smartgym.domain.model

data class AuthResponse(
    val token: String,
    val role: String,
    val nome: String
)
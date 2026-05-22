package com.academia.smartgym.domain.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class RegisterRequest(
    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    val nome: String,

    @field:NotBlank(message = "Email é obrigatório")
    @field:Email(message = "Email inválido")
    val email: String,

    @field:NotBlank(message = "CPF é obrigatório")
    @field:Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    val cpf: String,

    @field:NotBlank(message = "Telefone é obrigatório")
    @field:Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 dígitos")
    val telefone: String,

    @field:NotBlank(message = "Senha é obrigatória")
    @field:Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    val senha: String
)

data class PasswordResetToken(
    val id: Int? = null,
    val token: String,
    val usuarioId: Int,
    val expiracao: LocalDateTime
)
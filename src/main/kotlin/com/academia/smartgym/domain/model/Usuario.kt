package com.academia.smartgym.domain.model

import jakarta.validation.constraints.*

enum class UserRole {
    ALUNO, PROFESSOR, ADMIN
}

data class Usuario(
    val id: Int? = null,

    @field:NotBlank(message = "Nome é obrigatório!")
    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    val nome: String,

    @field:NotBlank(message = "Email é obrigatório")
    @field:Email(message = "Email inválido")
    val email: String,

    val role: UserRole = UserRole.ALUNO,

    @field:NotBlank(message = "CPF é obrigatório")
    @field:Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    val cpf: String,

    @field:NotBlank(message = "Telefone é obrigatório")
    val telefone: String,

    @field:Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    val senha: String? = null,

    // ── Plano ──────────────────────────────────────
    val plano: Plano? = null,
    val planoVencimento: String? = null,

    // ── Professor ──────────────────────────────────
    val professorId: Int? = null,
    val professorNome: String? = null,

    // ── Dados Físicos ──────────────────────────────
    val dataNascimento: String? = null,
    val altura: Double? = null,
    val peso: Double? = null,
    val dataCadastro: String? = null,

    // ── Status ─────────────────────────────────────
    val status: Boolean = true,
    val emailVerificado: Boolean = false
)
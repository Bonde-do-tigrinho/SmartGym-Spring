package com.academia.smartgym.domain.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

enum class UserRole {
    ALUNO,
    PROFESSOR,
    ADMIN
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
    @field:Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 dígitos")
    val telefone: String,

    @field:Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    val senha: String? = null,

    val plano: String? = null,
    val status: Boolean? = true,
    val treinoAtual: String? = null,
    val focoTreino: String? = null,
    val planoVencimento: String? = null,
    val planoValor: Double? = null
)
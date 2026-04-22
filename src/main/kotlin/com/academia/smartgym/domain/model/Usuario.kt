package com.academia.smartgym.domain.model

enum class UserRole {
    ALUNO,
    PROFESSOR,
    ADMIN
}
data class Usuario(
    val id: Int?,
    val nome: String,
    val email: String,
    val role: UserRole = UserRole.ALUNO,
    val cpf: String,
    val telefone: String,
    val plano: String,
    val status: Boolean,
    val treinoAtual: String?,
    val focoTreino: String?,
    val planoVencimento: String?,
    val planoValor: Double?
)
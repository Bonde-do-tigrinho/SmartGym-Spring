package com.academia.smartgym.domain.model

data class Usuario(
    val id: Int?,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val plano: String,
    val status: Boolean,
    val treinoAtual: String?,
    val focoTreino: String?,
    val planoVencimento: String?,
    val planoValor: Double?
)
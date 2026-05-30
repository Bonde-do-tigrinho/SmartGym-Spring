package com.academia.smartgym.domain.model

import java.time.LocalDateTime

data class Notificacao(
    val id: Int? = null,
    val titulo: String,
    val mensagem: String,
    val dataPostagem: LocalDateTime = LocalDateTime.now(),
    val dataExpiracao: LocalDateTime? = null,
    val categoria: CategoriaNotificacao
) {
    init {
        require(titulo.length >= 5) { "O título deve ter no mínimo 5 caracteres." }
        require(mensagem.length >= 5) { "A mensagem deve ter no mínimo 5 caracteres." }
    }
}
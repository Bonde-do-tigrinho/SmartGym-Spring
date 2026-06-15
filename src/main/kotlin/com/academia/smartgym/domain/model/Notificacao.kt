package com.academia.smartgym.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class Notificacao(
    val id: Int? = null,

    @field:NotBlank(message = "O título é obrigatório")
    @field:Size(min = 5, message = "O título deve ter no mínimo 5 caracteres.")
    val titulo: String,

    @field:NotBlank(message = "A mensagem é obrigatória")
    @field:Size(min = 5, message = "A mensagem deve ter no mínimo 5 caracteres.")
    val mensagem: String,

    @field:NotNull(message = "A data de postagem é obrigatória")
    val dataPostagem: LocalDateTime = LocalDateTime.now(),

    val dataExpiracao: LocalDateTime? = null,

    @field:NotNull(message = "A categoria da notificação é obrigatória")
    val categoria: CategoriaNotificacao
)
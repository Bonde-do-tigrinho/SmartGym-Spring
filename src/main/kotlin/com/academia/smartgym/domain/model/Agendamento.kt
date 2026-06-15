package com.academia.smartgym.domain.model

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class Agendamento(
    val id: Long? = null,

    @field:NotNull(message = "O ID do aluno é obrigatório")
    val alunoId: Long,

    @field:NotNull(message = "O ID da aula coletiva é obrigatório")
    val aulaColetivaId: Long,

    @field:NotNull(message = "A data do agendamento é obrigatória")
    val dataAgendamento: LocalDateTime = LocalDateTime.now(),

    @field:NotNull(message = "O status de confirmação é obrigatório")
    val confirmado: Boolean = true
)